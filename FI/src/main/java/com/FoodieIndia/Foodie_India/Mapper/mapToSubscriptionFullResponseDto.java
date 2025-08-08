package com.FoodieIndia.Foodie_India.Mapper;

import java.util.ArrayList;
import java.util.List;

import com.FoodieIndia.Foodie_India.DTO.Subscription.SubscriptionFullResponseDto;
import com.FoodieIndia.Foodie_India.model.Subscription;
import com.FoodieIndia.Foodie_India.model.UserSubscriptions;

public class mapToSubscriptionFullResponseDto {

    public SubscriptionFullResponseDto mapToSubscription(Subscription subscription) {

        SubscriptionFullResponseDto subscriptionFullResponseDto = new SubscriptionFullResponseDto();

        subscriptionFullResponseDto.setSubscription_id(subscription.getSubscription_id());
        subscriptionFullResponseDto.setSubscription_name(subscription.getSubscription_name());
        subscriptionFullResponseDto.setSubscription_description(subscription.getSubscription_description());
        subscriptionFullResponseDto.setSubscription_price(subscription.getSubscription_price());
        subscriptionFullResponseDto.setDurationInDays(subscription.getDurationInDays());
        subscriptionFullResponseDto.setCreatedAt(subscription.getCreatedAt());
        subscriptionFullResponseDto.setCreatedByUser(subscription.getCreatedByUser().getUserId());

        if (subscription.getPurchasedByUsers() != null) {
            List<UserSubscriptions> userSubscriptions = subscription.getPurchasedByUsers();
            List<Integer> userIds = new ArrayList<>();

            for (UserSubscriptions user : userSubscriptions) {
                userIds.add(user.getUser().getUserId());
            }

            subscriptionFullResponseDto.setPurchasedByUsers(userIds);
        }

        return subscriptionFullResponseDto;
    }
}
