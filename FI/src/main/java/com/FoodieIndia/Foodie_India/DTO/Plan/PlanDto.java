package com.FoodieIndia.Foodie_India.DTO.Plan;

import java.util.List;

import com.FoodieIndia.Foodie_India.Enums.Category;

import lombok.Data;

@Data
public class PlanDto {
    private String title;
    private String description;
    private Integer price;
    private Category category;
    private List<Integer> recepieIds;
}
