package com.FoodieIndia.Foodie_India.Service;

import org.springframework.stereotype.Service;

import com.FoodieIndia.Foodie_India.DTO.ApiResponse;

@Service
public class ResponseService {

    public <T> ApiResponse<T> sendResponse(T data, String message, int status) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        apiResponse.setStatus(status);
        return apiResponse;
    }

}
