package com.FoodieIndia.Foodie_India.DTO.Plan;

import java.time.LocalDateTime;
import java.util.List;

import com.FoodieIndia.Foodie_India.DTO.Recepie.RecepieFullResponseDto;

import lombok.Data;

@Data
public class PlanFullResponseDto {
    private Integer planid;
    private String title;
    private String description;
    private String thumbnaiUrl;
    private Integer price;
    private com.FoodieIndia.Foodie_India.Enums.Category category;
    private LocalDateTime createdAt;
    private Integer createdByUser;
    private List<RecepieFullResponseDto> recepies;
    private List<Integer> purchasedByUser;
    private String thumbnailPublicId;
}
