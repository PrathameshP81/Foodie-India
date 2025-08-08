package com.FoodieIndia.Foodie_India.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.FoodieIndia.Foodie_India.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    // List<User> findByRole(String role);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.createdPlans WHERE u.userId = :userId")
    Optional<User> findByIdWithCreatedPlans(@Param("userId") Integer userId);

}