package com.FoodieIndia.Foodie_India.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.FoodieIndia.Foodie_India.DTO.ApiResponse;
import com.FoodieIndia.Foodie_India.DTO.User.AdminFullResponseDto;
import com.FoodieIndia.Foodie_India.DTO.User.SuperUserFullResponseDto;
import com.FoodieIndia.Foodie_India.DTO.User.UserFullResponseDto;
import com.FoodieIndia.Foodie_India.Enums.Role;
import com.FoodieIndia.Foodie_India.Exception.AccessDeniedException;
import com.FoodieIndia.Foodie_India.Exception.UserDetailsNotFound;
import com.FoodieIndia.Foodie_India.Mapper.mapToUserFullResponseDto;
import com.FoodieIndia.Foodie_India.Repository.UserRepository;
import com.FoodieIndia.Foodie_India.model.User;

@Service
public class UserService {

    AuthService authService;
    UserRepository userRepository;
    AdminFullResponseDto adminFullResponseDto;
    UserFullResponseDto userFullResponseDto;
    SuperUserFullResponseDto superUserFullResponseDto;
    ResponseService responseService;

    UserService(AuthService authService, UserRepository userRepository, ResponseService responseService) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    public ResponseEntity<ApiResponse<?>> checkUserRoleAndSend(User user, String message) {
        if (user.getRole() == Role.ROLE_ADMIN) {
            adminFullResponseDto = (AdminFullResponseDto) new mapToUserFullResponseDto()
                    .mapToUserDto(user);

            return new ResponseEntity<>(
                    responseService.sendResponse(adminFullResponseDto, message,
                            HttpStatus.OK.value()),
                    HttpStatus.OK);
        } else if (user.getRole() == Role.ROLE_USER) {
            userFullResponseDto = (UserFullResponseDto) new mapToUserFullResponseDto().mapToUserDto(user);
            return new ResponseEntity<>(
                    responseService.sendResponse(userFullResponseDto, message,
                            HttpStatus.OK.value()),
                    HttpStatus.OK);
        } else {
            superUserFullResponseDto = (SuperUserFullResponseDto) new mapToUserFullResponseDto().mapToUserDto(user);
            return new ResponseEntity<>(
                    responseService.sendResponse(superUserFullResponseDto, message,
                            HttpStatus.OK.value()),
                    HttpStatus.OK);
        }
    }

    public ResponseEntity<ApiResponse<?>> getLogggedInUser() {
        try {

            User user = authService.getLoggedInUserDetails();

            if (user == null) {
                throw new AccessDeniedException("Failed To Get Logged in User Details");
            }

            return checkUserRoleAndSend(user, "User Fetch Successfully");

        } catch (Exception e) {
            System.out.println("Get Loggged In User " + e.getMessage());
            throw new UserDetailsNotFound("User Not found " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<Object> mappedList = new ArrayList<>();

            for (User user : users) {

                if (user.getRole() == Role.ROLE_ADMIN) {
                    adminFullResponseDto = (AdminFullResponseDto) new mapToUserFullResponseDto()
                            .mapToUserDto(user);
                    mappedList.add(adminFullResponseDto);

                } else if (user.getRole() == Role.ROLE_USER) {
                    userFullResponseDto = (UserFullResponseDto) new mapToUserFullResponseDto().mapToUserDto(user);
                    mappedList.add(userFullResponseDto);
                } else {
                    superUserFullResponseDto = (SuperUserFullResponseDto) new mapToUserFullResponseDto()
                            .mapToUserDto(user);
                    mappedList.add(superUserFullResponseDto);
                }
            }
            return new ResponseEntity<>(
                    responseService.sendResponse(mappedList, "Users Fetch Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Get ALl Users " + e.getMessage());
            throw new UserDetailsNotFound("User Not found" + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> getUserById(int userid) {
        try {
            User user = userRepository.findById(userid)
                    .orElseThrow(() -> new UserDetailsNotFound("User not found with Given id"));

            return checkUserRoleAndSend(user, "User Details Fetch Successfully");
        } catch (Exception e) {
            System.out.println("Get User By Id " + e.getMessage());
            throw new UserDetailsNotFound("User Not found " + e.getMessage());
        }

    }
}
