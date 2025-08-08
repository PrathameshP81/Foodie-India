package com.FoodieIndia.Foodie_India;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodieIndiaApplication {
	public static void main(String[] args) {
		SpringApplication.run(FoodieIndiaApplication.class, args);
	}
}
