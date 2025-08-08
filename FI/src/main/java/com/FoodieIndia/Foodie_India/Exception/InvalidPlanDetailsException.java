package com.FoodieIndia.Foodie_India.Exception;

public class InvalidPlanDetailsException extends RuntimeException {
    public InvalidPlanDetailsException(String messString) {
        super(messString);
    }
}
