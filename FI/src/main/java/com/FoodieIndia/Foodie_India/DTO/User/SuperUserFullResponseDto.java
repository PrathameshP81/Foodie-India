package com.FoodieIndia.Foodie_India.DTO.User;

import lombok.Data;

@Data
public class SuperUserFullResponseDto {
    private Integer userId;

    private String name;

    private String email;

    private String password;

    private String gender;

    private String phone;

    private String role;
}
