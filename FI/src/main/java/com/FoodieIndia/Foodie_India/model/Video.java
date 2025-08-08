package com.FoodieIndia.Foodie_India.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String videoUrl;

    private String duration;

    private String VidpublicId;

    @OneToOne
    @JoinColumn(name = "resource_id")
    private Resources resource;
}
