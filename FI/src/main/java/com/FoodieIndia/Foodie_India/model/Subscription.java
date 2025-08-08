package com.FoodieIndia.Foodie_India.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subscription_id;

    private String subscription_name;

    private String subscription_description;

    private Integer subscription_price;

    private Integer durationInDays;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id") // owns the FK
    User createdByUser;

    @OneToMany(mappedBy = "subscription")
    List<UserSubscriptions> purchasedByUsers = new ArrayList<>();

}
