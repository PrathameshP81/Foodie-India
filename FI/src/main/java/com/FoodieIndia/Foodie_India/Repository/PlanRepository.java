package com.FoodieIndia.Foodie_India.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodieIndia.Foodie_India.model.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {
    Plan findByTitle(String tString);

    List<Plan> findByCreatedBy_UserId(Integer userId);

}
