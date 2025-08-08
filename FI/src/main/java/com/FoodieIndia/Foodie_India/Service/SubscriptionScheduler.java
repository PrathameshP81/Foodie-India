package com.FoodieIndia.Foodie_India.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.FoodieIndia.Foodie_India.Repository.UserSubscriptionRepository;
import com.FoodieIndia.Foodie_India.model.UserSubscriptions;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class SubscriptionScheduler {

    private final UserSubscriptionRepository userSubscriptionRepository;

    // @Scheduled(cron = "0 0 0 * * *") // Runs once every day at midnight
    @Scheduled(cron = "0 * * * * *") // Every Second
    @Transactional
    public void deactivateExpiredSubscriptions() {
        LocalDateTime now = LocalDateTime.now();

        List<UserSubscriptions> subscriptions = userSubscriptionRepository.findAll();

        for (UserSubscriptions subscription : subscriptions) {
            if (subscription.isActive() && subscription.getEndTime().isBefore(now)) {

                subscription.setActive(false);

                userSubscriptionRepository.save(subscription);
            }
        }
    }
}