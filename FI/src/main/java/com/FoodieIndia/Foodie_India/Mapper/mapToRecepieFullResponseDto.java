package com.FoodieIndia.Foodie_India.Mapper;

import java.util.ArrayList;
import java.util.List;

import com.FoodieIndia.Foodie_India.DTO.Recepie.RecepieFullResponseDto;
import com.FoodieIndia.Foodie_India.model.Image;
import com.FoodieIndia.Foodie_India.model.Plan;
import com.FoodieIndia.Foodie_India.model.Recepie;
import com.FoodieIndia.Foodie_India.model.Resources;
import com.FoodieIndia.Foodie_India.model.Video;

public class mapToRecepieFullResponseDto {

    public RecepieFullResponseDto maptoRecepie(Recepie recepie) {
        RecepieFullResponseDto dto = new RecepieFullResponseDto();
        dto.setId(recepie.getRecepieId());
        dto.setTitle(recepie.getTitle());
        dto.setCategory(recepie.getCategory());

        dto.setCreatedByUser(recepie.getCreatedByUser().getUserId());

        List<Integer> planIds = new ArrayList<>();

        if (recepie.getPlans() != null && !recepie.getPlans().isEmpty()) {
            for (Plan plan : recepie.getPlans()) {
                planIds.add(plan.getPlanid());
            }
        }

        dto.setPlanIds(planIds);

        Resources resources = recepie.getResources();

        if (resources != null) {

            if (resources.getText() != null) {
                dto.setDescription(resources.getText().getContent());
                dto.setIngrediants(resources.getText().getIngrediants());
            }

            if (resources.getImage() != null) {
                Image image = resources.getImage();
                dto.setImageUrl(image.getImageUrl());
                dto.setAltText(image.getAltText());
                dto.setWidth(image.getWidth());
                dto.setImagePublicId(image.getImgpublicId());
                dto.setHeight(image.getHeight());
            }

            if (resources.getVideo() != null) {
                Video video = resources.getVideo();
                dto.setVideoUrl(video.getVideoUrl());
                dto.setVideoPublicId(video.getVidpublicId());
                dto.setDuration(video.getDuration());
            }
        }
        return dto;
    }

}
