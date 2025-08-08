package com.FoodieIndia.Foodie_India.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.FoodieIndia.Foodie_India.DTO.Plan.PlanFullResponseDto;
import com.FoodieIndia.Foodie_India.DTO.Recepie.RecepieFullResponseDto;
import com.FoodieIndia.Foodie_India.model.Plan;
import com.FoodieIndia.Foodie_India.model.User;

public class mapToPlanFullResponseDto {
    mapToRecepieFullResponseDto recepieMapper = new mapToRecepieFullResponseDto();

    public PlanFullResponseDto maptoPlan(Plan plan) {
        PlanFullResponseDto dto = new PlanFullResponseDto();
        dto.setPlanid(plan.getPlanid());
        dto.setTitle(plan.getTitle());
        dto.setDescription(plan.getDescription());
        dto.setThumbnaiUrl(plan.getThumbnailUrl());
        dto.setPrice(plan.getPrice());
        dto.setCategory(plan.getCategory());
        dto.setCreatedAt(plan.getCreatedAt());
        dto.setThumbnailPublicId(plan.getThumbnailPublicId());

        if (plan.getCreatedBy() != null) {
            dto.setCreatedByUser(plan.getCreatedBy().getUserId());
        }

        if (plan.getRecepies() != null && !plan.getRecepies().isEmpty()) {
            List<RecepieFullResponseDto> recepieDtos = plan.getRecepies()
                    .stream()
                    .map(recepieMapper::maptoRecepie) // Use your custom mapper method
                    .collect(Collectors.toList());
            dto.setRecepies(recepieDtos);
        } else {
            dto.setRecepies(new ArrayList<>());
        }

        if (plan.getPurchasedByUsers() != null) {
            List<User> users = plan.getPurchasedByUsers();
            List<Integer> userids = new ArrayList<>();

            for (User user : users) {
                userids.add(user.getUserId());
            }
            dto.setPurchasedByUser(userids);
        }
        return dto;
    }
}
