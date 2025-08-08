package com.FoodieIndia.Foodie_India.DTO.Recepie;

import com.FoodieIndia.Foodie_India.Enums.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecepieDto {
    String title;
    Category category;
    String recepiedescription;
    String ingrediants;
}
