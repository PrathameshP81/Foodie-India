package com.FoodieIndia.Foodie_India.DTO.Recepie;

import java.util.List;

import com.FoodieIndia.Foodie_India.Enums.Category;

import lombok.Data;

@Data
public class RecepieFullResponseDto {
    private Integer id;
    private String title;
    private Category category;

    private String description;
    private String ingrediants;

    private String imageUrl;
    private String altText;
    private int width;
    private int height;

    private String videoUrl;
    private String duration;

    private String imagePublicId;
    private String videoPublicId;
    private Integer createdByUser;
    private List<Integer> planIds;

}
