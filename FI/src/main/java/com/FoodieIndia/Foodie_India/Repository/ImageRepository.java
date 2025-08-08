package com.FoodieIndia.Foodie_India.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodieIndia.Foodie_India.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
