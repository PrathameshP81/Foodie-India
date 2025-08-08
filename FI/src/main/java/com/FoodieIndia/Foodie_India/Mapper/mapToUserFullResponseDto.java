package com.FoodieIndia.Foodie_India.Mapper;

import java.util.stream.Collectors;

import com.FoodieIndia.Foodie_India.DTO.User.AdminFullResponseDto;
import com.FoodieIndia.Foodie_India.DTO.User.SuperUserFullResponseDto;
import com.FoodieIndia.Foodie_India.DTO.User.UserFullResponseDto;
import com.FoodieIndia.Foodie_India.Enums.Role;
import com.FoodieIndia.Foodie_India.model.Plan;
import com.FoodieIndia.Foodie_India.model.Recepie;
import com.FoodieIndia.Foodie_India.model.User;
import com.FoodieIndia.Foodie_India.model.UserSubscriptions;

public class mapToUserFullResponseDto {
        public Object mapToUserDto(User user) {
                Role role = user.getRole();

                if (role == Role.ROLE_ADMIN) {
                        AdminFullResponseDto dto = new AdminFullResponseDto();
                        dto.setUserId(user.getUserId());
                        dto.setName(user.getName());
                        dto.setEmail(user.getEmail());
                        dto.setPassword(user.getPassword());
                        dto.setGender(user.getGender());
                        dto.setPhone(user.getPhone());
                        dto.setRole(role.name());

                        dto.setCreatedPlanIds(user.getCreatedPlans()
                                        .stream()
                                        .map(Plan::getPlanid)
                                        .collect(Collectors.toList()));

                        dto.setCreatedRecepieIds(user.getCreatedRecepies()
                                        .stream()
                                        .map(Recepie::getRecepieId)
                                        .collect(Collectors.toList()));

                        dto.setPurchasedSubscriptionIds(user.getPurchased_subscriptions()
                                        .stream()
                                        .map(UserSubscriptions::getId)
                                        .collect(Collectors.toList()));

                        return dto;

                } else if (role == Role.ROLE_USER) {
                        UserFullResponseDto dto = new UserFullResponseDto();
                        dto.setUserId(user.getUserId());
                        dto.setName(user.getName());
                        dto.setEmail(user.getEmail());
                        dto.setPassword(user.getPassword());
                        dto.setGender(user.getGender());
                        dto.setPhone(user.getPhone());
                        dto.setRole(role.name());

                        dto.setPurchasedPlanIds(user.getPurchased_plans()
                                        .stream()
                                        .map(Plan::getPlanid)
                                        .collect(Collectors.toList()));

                        return dto;

                } else if (role == Role.ROLE_SUPER_ADMIN) {
                        SuperUserFullResponseDto dto = new SuperUserFullResponseDto();
                        dto.setUserId(user.getUserId());
                        dto.setName(user.getName());
                        dto.setEmail(user.getEmail());
                        dto.setPassword(user.getPassword());
                        dto.setGender(user.getGender());
                        dto.setPhone(user.getPhone());
                        dto.setRole(role.name());

                        return dto;

                }

                throw new RuntimeException("Unsupported Role: " + role);
        }

}
