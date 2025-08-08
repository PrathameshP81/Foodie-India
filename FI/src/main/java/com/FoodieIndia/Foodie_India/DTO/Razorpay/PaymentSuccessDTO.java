package com.FoodieIndia.Foodie_India.DTO.Razorpay;

import lombok.Data;

@Data
public class PaymentSuccessDTO {
    private String razorpay_payment_id;
    private String razorpay_order_id;
    private String razorpay_signature;
}
