package com.FoodieIndia.Foodie_India.DTO.User;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
