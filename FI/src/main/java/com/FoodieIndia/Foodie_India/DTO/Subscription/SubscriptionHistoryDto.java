package com.FoodieIndia.Foodie_India.DTO.Subscription;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SubscriptionHistoryDto {
    private Integer id;

    private Integer user_id;

    private Integer subscription_id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime purchasedAt;

    private String payment_id;

    private String order_id;

    private boolean isActive;
}
