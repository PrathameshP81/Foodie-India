package com.FoodieIndia.Foodie_India.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.FoodieIndia.Foodie_India.Exception.AccessDeniedException;
import com.FoodieIndia.Foodie_India.Exception.UserDetailsNotFound;
import com.FoodieIndia.Foodie_India.Repository.UserRepository;
import com.FoodieIndia.Foodie_India.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("Access Denied! Unauthorized User"));

        if (user == null) {
            throw new UserDetailsNotFound("User not found with email: " + email);
        }

        return user;
    }

}