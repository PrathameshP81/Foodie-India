package com.FoodieIndia.Foodie_India.Exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String meString) {
        super(meString);
    }
}
