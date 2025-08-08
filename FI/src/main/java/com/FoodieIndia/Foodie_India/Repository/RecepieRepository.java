package com.FoodieIndia.Foodie_India.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FoodieIndia.Foodie_India.model.Recepie;
import com.FoodieIndia.Foodie_India.model.User;

@Repository
public interface RecepieRepository extends JpaRepository<Recepie, Integer> {
    Recepie findByTitle(String title);

    List<Recepie> findByCreatedByUser(User user);
}
