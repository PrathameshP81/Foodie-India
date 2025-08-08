package com.FoodieIndia.Foodie_India.Exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String meString) {
        super(meString);
    }
}
