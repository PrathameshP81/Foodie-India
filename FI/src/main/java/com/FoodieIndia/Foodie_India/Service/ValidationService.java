package com.FoodieIndia.Foodie_India.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.FoodieIndia.Foodie_India.DTO.Plan.PlanDto;
import com.FoodieIndia.Foodie_India.DTO.User.RegisterRequestDto;

@Service
public class ValidationService {

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public boolean validatePlan(MultipartFile file, PlanDto data, String operation) {
        try {
            if ("create plan".equalsIgnoreCase(operation)) {
                if (data == null
                        || data.getTitle() == null || data.getTitle().trim().isEmpty()
                        || data.getDescription() == null || data.getDescription().trim().isEmpty()
                        || data.getPrice() == null || data.getPrice() <= 0
                        || file == null || file.isEmpty()
                        || data.getCategory() == null
                        || !isImageFile(file)) {
                    return false;
                }
                return true;
            }

            if ("update plan".equalsIgnoreCase(operation)) {
                if (data == null
                        || data.getTitle() == null || data.getTitle().trim().isEmpty()
                        || data.getDescription() == null || data.getDescription().trim().isEmpty()
                        || data.getPrice() == null || data.getPrice() <= 0
                        || data.getCategory() == null) {
                    return false;
                }

                // file is optional, but if present must be a valid image
                if (file != null && (!isImageFile(file) || file.isEmpty())) {
                    return false;
                }
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserDataValid(RegisterRequestDto data) {
        if (data == null
                || data.getName() == null || data.getName().trim().isEmpty()
                || data.getEmail() == null || data.getEmail().trim().isEmpty()
                || data.getGender() == null || data.getGender().trim().isEmpty()
                || data.getPassword() == null || data.getPassword().trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
