package com.FoodieIndia.Foodie_India.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

public class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer resourceId;

    @OneToOne(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;

    @OneToOne(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private Video video;

    @OneToOne(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private Text text;
}
