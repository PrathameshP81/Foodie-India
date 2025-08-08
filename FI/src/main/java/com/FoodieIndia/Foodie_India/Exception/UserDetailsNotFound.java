package com.FoodieIndia.Foodie_India.Exception;

public class UserDetailsNotFound extends RuntimeException {
    public UserDetailsNotFound(String mesdString) {
        super(mesdString);
    }
}
