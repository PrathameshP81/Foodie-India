package com.FoodieIndia.Foodie_India.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import com.FoodieIndia.Foodie_India.Enums.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@ToString(exclude = { "purchasedByUsers", "createdBy", "recepies" })
@EqualsAndHashCode(exclude = { "purchasedByUsers", "createdBy", "recepies" })
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer planid;

    @Size(min = 2, max = 30, message = "Plan Name should be between 2 to 30 characters")
    private String title;

    private String description;

    private LocalDateTime createdAt;

    private String thumbnailUrl;

    private String thumbnailPublicId;

    private Integer price;

    Category category;

    // List of Users that purchased the plans
    @ManyToMany(mappedBy = "purchased_plans")
    List<User> purchasedByUsers = new ArrayList<>();

    // User who creates the plan
    @ManyToOne
    @JoinColumn(name = "created_by_user_id") // owns the FK
    User createdBy;

    // List of Recepies that Plan has
    @ManyToMany
    @JoinTable(name = "plan_recepies", joinColumns = @JoinColumn(name = "plan_id"), inverseJoinColumns = @JoinColumn(name = "recepie_id"))
    List<Recepie> recepies = new ArrayList<>();

}
