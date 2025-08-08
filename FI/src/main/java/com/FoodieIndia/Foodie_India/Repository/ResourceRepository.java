package com.FoodieIndia.Foodie_India.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodieIndia.Foodie_India.model.Resources;

@Repository
public interface ResourceRepository extends JpaRepository<Resources, Integer> {

}
