package com.FoodieIndia.Foodie_India.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.FoodieIndia.Foodie_India.DTO.ApiResponse;
import com.FoodieIndia.Foodie_India.DTO.Plan.PlanDto;
import com.FoodieIndia.Foodie_India.DTO.Plan.PlanFullResponseDto;
import com.FoodieIndia.Foodie_India.Enums.Category;
import com.FoodieIndia.Foodie_India.Enums.Role;
import com.FoodieIndia.Foodie_India.Exception.AccessDeniedException;
import com.FoodieIndia.Foodie_India.Exception.InvalidPlanDetailsException;
import com.FoodieIndia.Foodie_India.Exception.InvalidRecepieDetailsException;
import com.FoodieIndia.Foodie_India.Exception.PlanNotFoundException;
import com.FoodieIndia.Foodie_India.Exception.RecepiesNotFoundException;
import com.FoodieIndia.Foodie_India.Mapper.mapToPlanFullResponseDto;
import com.FoodieIndia.Foodie_India.Repository.PlanRepository;
import com.FoodieIndia.Foodie_India.Repository.RecepieRepository;
import com.FoodieIndia.Foodie_India.Repository.UserRepository;
import com.FoodieIndia.Foodie_India.model.Plan;
import com.FoodieIndia.Foodie_India.model.Recepie;
import com.FoodieIndia.Foodie_India.model.User;

@Service
public class PlanService {

    PlanRepository planRepository;
    UserRepository userRepository;
    SubscriptionService subscriptionService;
    RecepieRepository recepieRepository;
    CloudniaryService cloudniaryService;
    PlanFullResponseDto planFullResponseDto;
    ResponseService responseService;
    AuthService authService;
    ValidationService validationService;

    PlanService(PlanRepository planRepository, SubscriptionService subscriptionService, UserRepository userRepository,
            CloudniaryService cloudniaryService,
            RecepieRepository recepieRepository, AuthService authService, ValidationService validationService,
            ResponseService responseService) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.subscriptionService = subscriptionService;
        this.cloudniaryService = cloudniaryService;
        this.recepieRepository = recepieRepository;
        this.responseService = responseService;
        this.authService = authService;
        this.validationService = validationService;
    }

    public ResponseEntity<ApiResponse<?>> createPlan(MultipartFile file, PlanDto data) {
        try {

            User user = authService.getLoggedInUserDetails();

            if (user.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            if (!subscriptionService.getSubscriptionStatusofUser()) {
                throw new AccessDeniedException("User has no active subscriptions.");
            }

            if (!validationService.validatePlan(file, data, "create plan")) {
                throw new InvalidPlanDetailsException("Please Enter Valid Plan Details");
            }

            if (planRepository.findByTitle(data.getTitle()) != null) {
                throw new InvalidPlanDetailsException("Plan Already Exists");
            }

            // Upload thumbnail
            Map<?, ?> result = cloudniaryService.uploadFile(file);
            String thumbnailUrl = result.get("secure_url").toString();

            // Create Plan
            Plan plan = new Plan();
            plan.setCreatedAt(LocalDateTime.now());
            plan.setPrice(data.getPrice());
            plan.setTitle(data.getTitle());
            Category category = Category.valueOf(data.getCategory().name().toUpperCase());
            plan.setCategory(category);
            plan.setDescription(data.getDescription());
            plan.setThumbnailPublicId(result.get("public_id").toString());
            plan.setThumbnailUrl(thumbnailUrl);
            plan.setCreatedBy(user);

            if (data.getRecepieIds() != null && !data.getRecepieIds().isEmpty()) {

                List<Recepie> recepieList = new ArrayList<>();

                for (Integer id : data.getRecepieIds()) {
                    Recepie recepie = recepieRepository.findById(id)
                            .orElseThrow(() -> new RecepiesNotFoundException("Failed to fetch recipe with id " + id));

                    recepieList.add(recepie);
                }

                plan.setRecepies(recepieList);
            }

            List<Plan> userCreatedPlans = user.getCreatedPlans();
            userCreatedPlans.add(plan);

            Plan savedPlan = planRepository.save(plan);
            userRepository.save(user);

            PlanFullResponseDto planResponse = new mapToPlanFullResponseDto().maptoPlan(savedPlan);

            return new ResponseEntity<>(
                    responseService.sendResponse(planResponse, "Plan Created Successfully", HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            throw new InvalidRecepieDetailsException("Failed To Create Plan: " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> getAllPlans() {
        try {

            User user = authService.getLoggedInUserDetails();
            List<Plan> plans;

            if (user.getRole() == Role.ROLE_ADMIN) {
                plans = planRepository.findByCreatedBy_UserId(user.getUserId());
            } else {
                plans = planRepository.findAll();
            }

            List<PlanFullResponseDto> mappedList = new ArrayList<>();

            for (Plan plan : plans) {
                planFullResponseDto = new mapToPlanFullResponseDto().maptoPlan(plan);
                mappedList.add(planFullResponseDto);
            }

            return new ResponseEntity<>(
                    responseService.sendResponse(mappedList, "Plans Fetched Successfully", HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new PlanNotFoundException("Failed To Fetch the Plan " + e.getMessage());

        }
    }

    public ResponseEntity<ApiResponse<?>> getPlanById(int id) {
        try {
            User user = authService.getLoggedInUserDetails();

            Plan plan = planRepository.findById(id)
                    .orElseThrow(() -> new PlanNotFoundException("Failed to fetch Plan with id " + id));

            if (user.getRole() == Role.ROLE_ADMIN && !plan.getCreatedBy().getUserId().equals(user.getUserId())) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            PlanFullResponseDto planFullResponseDto = new mapToPlanFullResponseDto().maptoPlan(plan);

            return ResponseEntity.ok(
                    responseService.sendResponse(planFullResponseDto, "Plan fetched successfully",
                            HttpStatus.OK.value()));

        } catch (Exception e) {

            System.out.println("GetPlan By Id " + e.getMessage());
            throw new PlanNotFoundException("Something went wrong while fetching plan " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> deletePlanById(int id) {
        try {
            Plan plan = planRepository.findById(id)
                    .orElseThrow(() -> new PlanNotFoundException("Failed to fetch plan with id " + id));

            User user = authService.getLoggedInUserDetails();

            if (user.getRole() != Role.ROLE_ADMIN
                    || !plan.getCreatedBy().getUserId().equals(user.getUserId())) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            if (!subscriptionService.getSubscriptionStatusofUser()) {
                throw new AccessDeniedException("User has no active subscriptions.");
            }

            PlanFullResponseDto planFullResponseDto = new mapToPlanFullResponseDto().maptoPlan(plan);
            List<Recepie> recepies = plan.getRecepies();

            for (Recepie recepie : recepies) {
                recepie.getPlans().remove(plan);
            }

            recepieRepository.saveAll(recepies);

            planRepository.delete(plan);

            cloudniaryService.deleteFile(planFullResponseDto.getThumbnailPublicId(),
                    "image");

            return new ResponseEntity<>(
                    responseService.sendResponse(planFullResponseDto, "Plan Deleted Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Delete Plan");
            throw new PlanNotFoundException("Failed to detele Plan " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> updatePlanById(int id, MultipartFile file, PlanDto data) {
        try {

            User user = authService.getLoggedInUserDetails();

            if (user.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            if (!validationService.validatePlan(file, data, "update plan")) {
                throw new InvalidPlanDetailsException("Please Enter Valid Plan Details");
            }

            Plan plan = planRepository.findById(id)
                    .orElseThrow(() -> new InvalidPlanDetailsException("Failed to fetch Plan with id " + id));

            if (!plan.getCreatedBy().getUserId().equals(user.getUserId())) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            if (!subscriptionService.getSubscriptionStatusofUser()) {
                throw new AccessDeniedException("User has no active subscriptions.");
            }

            if (file != null && !file.isEmpty()) {
                // Delete the old image
                cloudniaryService.deleteFile(plan.getThumbnailPublicId(), "image");

                // Upload new image
                Map<?, ?> result = cloudniaryService.uploadFile(file);
                plan.setThumbnailUrl(result.get("secure_url").toString());
                plan.setThumbnailPublicId(result.get("public_id").toString());
            }

            plan.setTitle(data.getTitle());
            plan.setDescription(data.getDescription());
            plan.setPrice(data.getPrice());
            Category category = Category.valueOf(data.getCategory().name().toUpperCase());
            plan.setCategory(category);

            if (data.getRecepieIds() != null && !data.getRecepieIds().isEmpty()) {

                List<Integer> incomingRecepieIds = data.getRecepieIds();

                List<Recepie> currentRecepies = plan.getRecepies();
                List<Integer> currentRecepieIds = currentRecepies.stream()
                        .map(Recepie::getRecepieId)
                        .collect(Collectors.toList());

                List<Integer> recepieIdsToAdd = new ArrayList<>(incomingRecepieIds);
                recepieIdsToAdd.removeAll(currentRecepieIds);

                Set<Integer> recepieIdsToRemove = new HashSet<>(currentRecepieIds);
                recepieIdsToRemove.removeAll(incomingRecepieIds);

                currentRecepies.removeIf(recepie -> recepieIdsToRemove.contains(recepie.getRecepieId()));

                for (Integer recepieId : recepieIdsToAdd) {
                    Recepie recepie = recepieRepository.findById(recepieId)
                            .orElseThrow(
                                    () -> new RecepiesNotFoundException("Recepie not found with ID: " + recepieId));
                    currentRecepies.add(recepie);
                }
                plan.setRecepies(currentRecepies);
            } else {
                plan.setRecepies(new ArrayList<>());
            }
            Plan savedPlan = planRepository.save(plan);
            planFullResponseDto = new mapToPlanFullResponseDto().maptoPlan(savedPlan);

            return new ResponseEntity<>(
                    responseService.sendResponse(planFullResponseDto, "Plan Updated Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            throw new PlanNotFoundException("Failed to Update Plan " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> purchasePlan(int planid) {
        try {

            User user = authService.getLoggedInUserDetails();

            if (user.getRole() != Role.ROLE_USER) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            Plan plan = planRepository.findById(planid)
                    .orElseThrow(() -> new PlanNotFoundException("Failed to fetch Plan with id " + planid));

            planFullResponseDto = new mapToPlanFullResponseDto().maptoPlan(plan);

            if (user.getRole() == Role.ROLE_USER) {
                List<User> PurcheasedPlanByuser = plan.getPurchasedByUsers();

                if (PurcheasedPlanByuser.contains(user)) {
                    return new ResponseEntity<>(
                            responseService.sendResponse(planFullResponseDto, "User has already purchased this plan",
                                    HttpStatus.BAD_REQUEST.value()),
                            HttpStatus.BAD_REQUEST);
                }

                PurcheasedPlanByuser.add(user);

                plan.setPurchasedByUsers(PurcheasedPlanByuser);
                user.getPurchased_plans().add(plan);
                Plan savedPlan = planRepository.save(plan); // Save changes
                planFullResponseDto = new mapToPlanFullResponseDto().maptoPlan(savedPlan);

                return new ResponseEntity<>(
                        responseService.sendResponse(planFullResponseDto, "Plan Purchased Successfully",
                                HttpStatus.OK.value()),
                        HttpStatus.OK);
            } else {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }
        } catch (Exception e) {
            throw new PlanNotFoundException("Failed to Purchase Plan " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> getAllPurchasedPlansByUser() {
        try {

            User user = authService.getLoggedInUserDetails();

            List<PlanFullResponseDto> mappedList = new ArrayList<>();

            if (user.getRole() != Role.ROLE_USER) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            if (user.getPurchased_plans() != null) {
                List<Plan> userPlans = user.getPurchased_plans();

                for (Plan plan : userPlans) {
                    planFullResponseDto = new mapToPlanFullResponseDto().maptoPlan(plan);
                    mappedList.add(planFullResponseDto);
                }
            }

            return new ResponseEntity<>(
                    responseService.sendResponse(mappedList, "Plans Fetched Successfully", HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new PlanNotFoundException("Failed To Fetch the Plan " + e.getMessage());

        }
    }
}
