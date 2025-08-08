package com.FoodieIndia.Foodie_India.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

    @Value("${cloud_name}")
    String cloud_name;

    @Value("${api_key}")
    String api_key;

    @Value("${api_secret}")
    String api_secret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloud_name);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);
        config.put("secure", "true"); // Use HTTPS
        return new Cloudinary(config);
    }
}