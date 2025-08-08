package com.FoodieIndia.Foodie_India.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodieIndia.Foodie_India.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

}
