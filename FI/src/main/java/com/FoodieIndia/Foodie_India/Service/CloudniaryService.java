package com.FoodieIndia.Foodie_India.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudniaryService {

    Cloudinary cloudinary;

    CloudniaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<?, ?> uploadFile(MultipartFile file) throws IOException {

        String folder = "foodie-india/data";

        Map<String, Object> options = new HashMap<>();

        options.put("folder", folder); // e.g., "foodie-india/images"
        options.put("resource_type", "auto"); // Auto-detect image/video

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                options);

        return uploadResult;
    }

    public void deleteFile(String publicId, String resourceType) throws IOException {
        cloudinary.uploader().destroy(publicId, Map.of("resource_type", resourceType));
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getMediaFromCloudinary(String publicId) throws Exception {
        return (Map<String, Object>) cloudinary.api().resource(publicId, ObjectUtils.emptyMap());
    }

    public boolean checkMediaFromCloudinary(String publicId) {
        try {
            cloudinary.api().resource(publicId, ObjectUtils.emptyMap());
            return true; // Media exists and was fetched
        } catch (Exception e) {
            System.out.println("Error fetching media from Cloudinary: " + e.getMessage());
            return false; // Media not found or error occurred
        }
    }

}
