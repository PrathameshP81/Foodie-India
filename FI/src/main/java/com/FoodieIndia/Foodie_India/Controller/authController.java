package com.FoodieIndia.Foodie_India.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FoodieIndia.Foodie_India.DTO.User.LoginRequestDto;
import com.FoodieIndia.Foodie_India.DTO.User.RegisterRequestDto;
import com.FoodieIndia.Foodie_India.Service.AuthService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class authController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody RegisterRequestDto data) {
        return authService.RegisterUser(data);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequestDto data) {
        return authService.LoginUser(data);
    }

    @PostMapping("/{amount}")
    public ResponseEntity<?> createOrder(@PathVariable("amount") int amount) {
        return authService.createOrder(amount);
    }

}
