package com.FoodieIndia.Foodie_India.Exception;

public class PlanNotFoundException extends RuntimeException {

    public PlanNotFoundException(String meString) {
        super(meString);
    }
}
