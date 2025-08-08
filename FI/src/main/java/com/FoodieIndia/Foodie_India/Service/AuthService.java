package com.FoodieIndia.Foodie_India.Service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FoodieIndia.Foodie_India.DTO.ApiResponse;
import com.FoodieIndia.Foodie_India.DTO.User.AdminFullResponseDto;
import com.FoodieIndia.Foodie_India.DTO.User.LoginRequestDto;
import com.FoodieIndia.Foodie_India.DTO.User.LoginResponseDto;
import com.FoodieIndia.Foodie_India.DTO.User.RegisterRequestDto;
import com.FoodieIndia.Foodie_India.DTO.User.UserFullResponseDto;
import com.FoodieIndia.Foodie_India.Enums.Role;
import com.FoodieIndia.Foodie_India.Exception.AccessDeniedException;
import com.FoodieIndia.Foodie_India.Exception.InvalidUserDetailsException;
import com.FoodieIndia.Foodie_India.Exception.UserAlreadyExistsException;
import com.FoodieIndia.Foodie_India.Exception.UserDetailsNotFound;
import com.FoodieIndia.Foodie_India.Mapper.mapToUserFullResponseDto;
import com.FoodieIndia.Foodie_India.Repository.UserRepository;
import com.FoodieIndia.Foodie_India.auth.JwtUtils;
import com.FoodieIndia.Foodie_India.model.User;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.springframework.security.core.Authentication;

@Service
public class AuthService {

    @Value("${razor_api_key}")
    String razor_api_key;

    @Value("${razor_api_secret}")
    String razor_api_secret;

    UserRepository userRepository;
    UserFullResponseDto userFullResponseDto;
    AdminFullResponseDto adminFullResponseDto;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    ResponseService responseService;
    JwtUtils jwtUtils;
    ValidationService validationService;

    AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, ValidationService validationService, JwtUtils jwtUtils,
            ResponseService responseService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
        this.responseService = responseService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<ApiResponse<?>> RegisterUser(RegisterRequestDto data) {
        try {

            if (!validationService.isUserDataValid(data)) {
                throw new InvalidUserDetailsException("Please Enter Valid User Details");
            }

            if (userRepository.findByEmail(data.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User already registered: " + data.getEmail());
            }

            User user = new User();

            user.setName(data.getName());
            user.setEmail(data.getEmail());
            user.setGender(data.getGender());
            user.setPhone(data.getPhone());

            String hashedPass = passwordEncoder.encode(data.getPassword());
            user.setPassword(hashedPass);

            if (data.getRole().equals("ROLE_ADMIN")) {
                user.setRole(Role.ROLE_ADMIN);
            } else {
                user.setRole(Role.ROLE_USER);
            }

            // user.setRole(Role.ROLE_SUPER_ADMIN);
            User savedUser = userRepository.save(user);

            // return new ResponseEntity<>(
            // responseService.sendResponse(null, "User Register Successfully",
            // HttpStatus.OK.value()),
            // HttpStatus.OK);

            if (user.getRole() == Role.ROLE_ADMIN) {
                adminFullResponseDto = (AdminFullResponseDto) new mapToUserFullResponseDto().mapToUserDto(savedUser);

                return new ResponseEntity<>(
                        responseService.sendResponse(adminFullResponseDto, "User Register  Successfully",
                                HttpStatus.OK.value()),
                        HttpStatus.OK);
            } else {
                userFullResponseDto = (UserFullResponseDto) new mapToUserFullResponseDto().mapToUserDto(savedUser);
                return new ResponseEntity<>(
                        responseService.sendResponse(userFullResponseDto, "User Register  Successfully",
                                HttpStatus.OK.value()),
                        HttpStatus.OK);
            }

        } catch (Exception e) {
            System.out.println("Failed to Register User " + e.getMessage());
            throw new InvalidUserDetailsException("Failed to Register User " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> LoginUser(LoginRequestDto data) {
        try {
            if (data.getEmail() == null || data.getEmail().trim().isEmpty() ||
                    data.getPassword() == null || data.getPassword().trim().isEmpty()) {
                throw new InvalidUserDetailsException("Please Enter Valid User Details");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.getEmail(), data.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtils.generateToken(data.getEmail());
            User user = userRepository.findByEmail(data.getEmail())
                    .orElseThrow(() -> new AccessDeniedException("Access Denied! Unauthorized User"));

            LoginResponseDto loginResponse = new LoginResponseDto();
            loginResponse.setEmail(user.getEmail());
            loginResponse.setUserId(user.getUserId());
            loginResponse.setToken(token);

            return new ResponseEntity<>(
                    responseService.sendResponse(loginResponse, "User Login Successfully", HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            throw new InvalidUserDetailsException("Failed to Login User: " + e.getMessage());
        }
    }

    public User getLoggedInUserDetails() {
        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
                User user = (User) auth.getPrincipal();

                return userRepository.findByIdWithCreatedPlans(user.getUserId())
                        .orElseThrow(() -> new UserDetailsNotFound("User not found"));

            }
            return null;

        } catch (Exception e) {
            System.out.println("Get Loggged In User " + e.getMessage());
            throw new UserDetailsNotFound("User Not found" + e.getMessage());
        }
    }

    public ResponseEntity<?> createOrder(int amount) {
        try {
            RazorpayClient client = new RazorpayClient(razor_api_key, razor_api_secret);

            JSONObject options = new JSONObject();
            options.put("amount", amount * 100);
            options.put("currency", "INR");
            options.put("receipt", "receipt_" + System.currentTimeMillis());

            JSONObject notes = new JSONObject();
            options.put("notes", notes);

            Order order = client.orders.create(options);

            return ResponseEntity.ok(Map.of(
                    "orderId", order.get("id"),
                    "amount", order.get("amount")));

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to create order"));
        }
    }

}
