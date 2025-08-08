package com.FoodieIndia.Foodie_India.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FoodieIndia.Foodie_India.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/loggedInUser")
    public ResponseEntity<?> getCurrentLoggedInUserDetails() {
        return userService.getLogggedInUser();
    }

    @GetMapping()
    public ResponseEntity<?> getAllUserDetails() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userid}")
    public ResponseEntity<?> getUserByGivenId(@PathVariable("userid") int userid) {
        return userService.getUserById(userid);
    }

}
