package com.FoodieIndia.Foodie_India.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.FoodieIndia.Foodie_India.DTO.ApiResponse;
import com.FoodieIndia.Foodie_India.DTO.Recepie.RecepieDto;
import com.FoodieIndia.Foodie_India.DTO.Recepie.RecepieFullResponseDto;
import com.FoodieIndia.Foodie_India.Enums.Category;
import com.FoodieIndia.Foodie_India.Enums.Role;
import com.FoodieIndia.Foodie_India.Exception.AccessDeniedException;
import com.FoodieIndia.Foodie_India.Exception.InvalidRecepieDetailsException;
import com.FoodieIndia.Foodie_India.Exception.RecepiesNotFoundException;
import com.FoodieIndia.Foodie_India.Mapper.mapToRecepieFullResponseDto;
import com.FoodieIndia.Foodie_India.Repository.RecepieRepository;
import com.FoodieIndia.Foodie_India.Repository.ResourceRepository;
import com.FoodieIndia.Foodie_India.Repository.UserRepository;
import com.FoodieIndia.Foodie_India.Repository.VideoRepository;
import com.FoodieIndia.Foodie_India.model.Image;
import com.FoodieIndia.Foodie_India.model.Recepie;
import com.FoodieIndia.Foodie_India.model.Resources;
import com.FoodieIndia.Foodie_India.model.Text;
import com.FoodieIndia.Foodie_India.model.User;
import com.FoodieIndia.Foodie_India.model.Video;

@Service
public class RecepieService {

    CloudniaryService cloudniaryService;
    VideoRepository videoRepository;
    SubscriptionService subscriptionService;
    UserRepository userRepository;
    RecepieRepository recepieRepository;
    ResourceRepository resourceRepository;
    RecepieFullResponseDto recepieFullResponseDto;
    AuthService authService;
    ResponseService responseService;

    RecepieService(CloudniaryService cloudniaryService, SubscriptionService subscriptionService,
            VideoRepository videoRepository,
            RecepieRepository recepieRepository, ResourceRepository resourceRepository,
            AuthService authService, UserRepository userRepository, ResponseService responseService) {
        this.cloudniaryService = cloudniaryService;
        this.subscriptionService = subscriptionService;
        this.recepieRepository = recepieRepository;
        this.videoRepository = videoRepository;
        this.resourceRepository = resourceRepository;
        this.authService = authService;
        this.responseService = responseService;
        this.userRepository = userRepository;
    }

    public boolean isRecepieDataValid(RecepieDto data, List<MultipartFile> fileList) {
        if (data == null
                || data.getTitle() == null || data.getTitle().trim().isEmpty()
                || data.getCategory() == null
                || data.getRecepiedescription() == null || data.getRecepiedescription().trim().isEmpty()
                || fileList == null || fileList.size() < 2) {
            return false;
        }

        boolean hasImage = false;
        boolean hasVideo = false;

        for (MultipartFile file : fileList) {
            String fileName = file.getOriginalFilename();
            if (fileName != null) {
                String lower = fileName.toLowerCase();
                if (lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png")) {
                    hasImage = true;
                } else if (lower.endsWith(".mp4") || lower.endsWith(".mov") || lower.endsWith(".avi")
                        || lower.endsWith(".mkv")) {
                    hasVideo = true;
                }
            }
        }

        return hasImage && hasVideo;
    }

    public Resources saveResources(List<MultipartFile> fileList, RecepieDto data, Resources existingResource) {
        try {
            Image image = null;
            Video video = null;

            for (MultipartFile file : fileList) {
                Map<?, ?> result = cloudniaryService.uploadFile(file);
                String resourceType = (String) result.get("resource_type");

                switch (resourceType) {
                    case "video":
                        video = new Video();
                        video.setVideoUrl(result.get("secure_url").toString());
                        video.setDuration(result.get("duration").toString());
                        video.setVidpublicId(result.get("public_id").toString());
                        break;

                    case "image":
                        image = new Image();
                        image.setImageUrl(result.get("secure_url").toString());
                        image.setAltText(data.getTitle());
                        image.setWidth((Integer) result.get("width"));
                        image.setHeight((Integer) result.get("height"));
                        image.setImgpublicId(result.get("public_id").toString());
                        break;

                    default:
                        // Skip unknown file types
                        break;
                }
            }

            Text text = new Text();
            text.setContent(data.getRecepiedescription().trim().toLowerCase());
            text.setIngrediants(data.getIngrediants().trim().toLowerCase());

            Resources resource = (existingResource != null) ? existingResource : new Resources();

            if (existingResource != null) {
                resource.setImage(null);
                resource.setVideo(null);
                resource.setText(null);
            }

            if (image != null) {
                image.setResource(resource);
                resource.setImage(image);
            }
            if (video != null) {
                video.setResource(resource);
                resource.setVideo(video);
            }
            text.setResource(resource);
            resource.setText(text);

            return resource;

        } catch (Exception e) {
            return null;
        }

    }

    public boolean isRecepieAlreadyCreatedByUser(String title, User user) {

        Recepie isAlreadyExist = recepieRepository.findByTitle(title.trim().toLowerCase());

        if (isAlreadyExist == null)
            return false;

        boolean isCurrentOwner = isAlreadyExist.getCreatedByUser().getUserId().equals(user.getUserId());

        if (isCurrentOwner) {
            return true;
        }
        return false;
    }

    public ResponseEntity<ApiResponse<?>> createRecepie(List<MultipartFile> fileList, RecepieDto data) {
        try {

            User user = authService.getLoggedInUserDetails();

            if (!Role.ROLE_ADMIN.equals(user.getRole())) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            if (!subscriptionService.getSubscriptionStatusofUser()) {
                throw new AccessDeniedException("User has no active subscriptions.");
            }

            if (!isRecepieDataValid(data, fileList)) {
                throw new InvalidRecepieDetailsException("Please Enter Valid Recepie Details");
            }

            if (isRecepieAlreadyCreatedByUser(data.getTitle(), user)) {
                throw new InvalidRecepieDetailsException("Recepie Already Exist with Given id");
            }

            Resources resource = saveResources(fileList, data, null);

            if (resource == null) {
                throw new InvalidRecepieDetailsException("Please Enter Valid Recepie Details");
            }

            Recepie recepie = new Recepie();
            recepie.setTitle(data.getTitle().trim().toLowerCase());
            Category category = Category.valueOf(data.getCategory().name().toUpperCase());
            recepie.setCategory(category);
            recepie.setCreatedByUser(user);
            recepie.setResources(resource);

            Recepie savedRecepie = recepieRepository.save(recepie);

            recepieFullResponseDto = new mapToRecepieFullResponseDto().maptoRecepie(savedRecepie);

            return new ResponseEntity<>(
                    responseService.sendResponse(recepieFullResponseDto, "Recepie Created Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {

            System.out.println("Create Recepie Method " + e.getMessage());
            throw new RecepiesNotFoundException("Failed To Create Recepie " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> getRecepies() {
        try {
            User user = authService.getLoggedInUserDetails();
            List<Recepie> recepies;

            if (user.getRole() == Role.ROLE_ADMIN) {
                recepies = recepieRepository.findByCreatedByUser(user);
            } else {
                recepies = recepieRepository.findAll();
            }

            List<RecepieFullResponseDto> mappedList = new ArrayList<>();

            for (Recepie recepie : recepies) {
                recepieFullResponseDto = new mapToRecepieFullResponseDto().maptoRecepie(recepie);
                mappedList.add(recepieFullResponseDto);
            }

            return new ResponseEntity<>(
                    responseService.sendResponse(mappedList, "Recepies Fetched Successfully", HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new RecepiesNotFoundException("Failed To Fetch the Recepies " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> getRecepieById(int id) {
        try {
            User user = authService.getLoggedInUserDetails();
            Recepie recepie = recepieRepository.findById(id)
                    .orElseThrow(() -> new RecepiesNotFoundException("Failed to fetch recipe with id " + id));

            if (user.getRole() == Role.ROLE_ADMIN && !recepie.getCreatedByUser().getUserId().equals(user.getUserId())) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            RecepieFullResponseDto recepieFullResponseDto = new mapToRecepieFullResponseDto().maptoRecepie(recepie);

            return ResponseEntity.ok(
                    responseService.sendResponse(recepieFullResponseDto, "Recipe fetched successfully",
                            HttpStatus.OK.value()));

        } catch (Exception e) {
            // Log unexpected error
            System.out.println("Unexpected error: " + e.getMessage());
            throw new RecepiesNotFoundException("Something went wrong while fetching recipe" + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteRecpieById(int id) {
        try {
            User user = authService.getLoggedInUserDetails();

            if (user.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("User has no active subscriptions.");
            }

            if (!subscriptionService.getSubscriptionStatusofUser()) {
                throw new AccessDeniedException("User has no active subscriptions.");
            }

            Recepie recepie = recepieRepository.findById(id)
                    .orElseThrow(() -> new RecepiesNotFoundException("Failed to fetch recipe with id " + id));

            if (!recepie.getCreatedByUser().getUserId().equals(user.getUserId())) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            recepieFullResponseDto = new mapToRecepieFullResponseDto().maptoRecepie(recepie);

            recepieRepository.delete(recepie);

            cloudniaryService.deleteFile(recepieFullResponseDto.getVideoPublicId(), "video");
            cloudniaryService.deleteFile(recepieFullResponseDto.getImagePublicId(), "image");

            return new ResponseEntity<>(
                    responseService.sendResponse(recepieFullResponseDto, "Recepie Deleted Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RecepiesNotFoundException("Failed to detele recipe " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> updateRecepie(int id, List<MultipartFile> fileList, RecepieDto data) {
        try {
            User user = authService.getLoggedInUserDetails();

            if (user.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("User has no active subscriptions.");
            }

            if (!subscriptionService.getSubscriptionStatusofUser()) {
                throw new AccessDeniedException("User has no active subscriptions.");
            }

            if (!isRecepieDataValid(data, fileList)) {
                throw new InvalidRecepieDetailsException("Please Enter Valid Recepie Details");
            }

            Recepie recepie = recepieRepository.findById(id)
                    .orElseThrow(() -> new RecepiesNotFoundException("Failed to fetch recipe with id " + id));

            if (!recepie.getCreatedByUser().getUserId().equals(user.getUserId())) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            recepieFullResponseDto = new mapToRecepieFullResponseDto().maptoRecepie(recepie);

            cloudniaryService.deleteFile(recepieFullResponseDto.getVideoPublicId(), "video");
            cloudniaryService.deleteFile(recepieFullResponseDto.getImagePublicId(), "image");

            recepie.setTitle(data.getTitle());
            Category category = Category.valueOf(data.getCategory().name().toUpperCase());
            recepie.setCategory(category);

            Resources resource = saveResources(fileList, data, recepie.getResources());
            recepie.setResources(resource);

            Recepie savedRecepie = recepieRepository.save(recepie);

            recepieFullResponseDto = new mapToRecepieFullResponseDto().maptoRecepie(savedRecepie);

            return new ResponseEntity<>(
                    responseService.sendResponse(recepieFullResponseDto, "Recepie Updated Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Create Recepie Method " + e.getMessage());
            throw new InvalidRecepieDetailsException("Failed To Update Recepie " + e.getMessage());

        }

    }

}
