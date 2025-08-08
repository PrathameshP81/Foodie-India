package com.FoodieIndia.Foodie_India.DTO.Subscription;

import lombok.Data;

@Data
public class SubscriptionDto {

    private String subscription_name;

    private String subscription_description;

    private Integer subscription_price;

    private Integer durationInDays;
}
