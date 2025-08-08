package com.FoodieIndia.Foodie_India.DTO.User;

import java.util.List;

import lombok.Data;

@Data
public class AdminFullResponseDto {
    private Integer userId;

    private String name;

    private String email;

    private String password;

    private String gender;

    private String phone;

    private String role;

    private List<Integer> purchasedPlanIds;

    private List<Integer> createdPlanIds;

    private List<Integer> createdRecepieIds;

    private List<Integer> purchasedSubscriptionIds;

}
