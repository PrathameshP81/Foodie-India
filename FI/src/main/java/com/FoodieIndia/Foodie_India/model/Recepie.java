package com.FoodieIndia.Foodie_India.model;

import java.util.ArrayList;

import java.util.List;

import com.FoodieIndia.Foodie_India.Enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@ToString(exclude = { "plans", "resources" })
@EqualsAndHashCode(exclude = { "plans", "resources" })
public class Recepie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recepieId;

    private String title;

    @Enumerated(EnumType.STRING)
    Category category;

    // List of Plans where Recepie Recides
    @ManyToMany(mappedBy = "recepies")
    @JsonIgnore
    List<Plan> plans = new ArrayList<>();

    // User who Creates a Recepie
    @ManyToOne
    @JoinColumn(name = "created_by_user_id") // owns the FK
    User createdByUser;

    // List of Resources
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id")
    @JsonIgnore
    Resources resources;

}
