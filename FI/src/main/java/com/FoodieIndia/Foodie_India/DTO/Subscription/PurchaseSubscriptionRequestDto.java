package com.FoodieIndia.Foodie_India.DTO.Subscription;

import lombok.Data;

@Data
public class PurchaseSubscriptionRequestDto {
    String razorpay_payment_id;
    String razorpay_order_id;
}
