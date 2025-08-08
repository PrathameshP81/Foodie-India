package com.FoodieIndia.Foodie_India.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String meString) {
        super(meString);
    }
}
