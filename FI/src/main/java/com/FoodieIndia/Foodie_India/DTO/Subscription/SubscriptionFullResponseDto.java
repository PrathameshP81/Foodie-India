package com.FoodieIndia.Foodie_India.DTO.Subscription;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class SubscriptionFullResponseDto {
    private Integer subscription_id;

    private String subscription_name;

    private String subscription_description;

    private Integer subscription_price;

    private Integer durationInDays;

    private LocalDateTime createdAt;

    private Integer createdByUser;

    List<Integer> purchasedByUsers;
}
