package com.FoodieIndia.Foodie_India.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodieIndia.Foodie_India.model.User;
import com.FoodieIndia.Foodie_India.model.UserSubscriptions;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptions, Integer> {
    List<UserSubscriptions> findByuser(User user);

}
