package com.FoodieIndia.Foodie_India.DTO.User;

import lombok.Data;

@Data
public class LoginResponseDto {

    private Integer userId;

    private String email;

    private String token;

}
