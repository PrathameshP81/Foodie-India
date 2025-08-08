package com.FoodieIndia.Foodie_India.Exception;

public class InvalidUserDetailsException extends RuntimeException {
    public InvalidUserDetailsException(String message) {
        super(message);
    }
}
