package com.FoodieIndia.Foodie_India.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.FoodieIndia.Foodie_India.DTO.ApiResponse;
import com.FoodieIndia.Foodie_India.DTO.Subscription.PurchaseSubscriptionRequestDto;
import com.FoodieIndia.Foodie_India.DTO.Subscription.SubscriptionDto;
import com.FoodieIndia.Foodie_India.DTO.Subscription.SubscriptionFullResponseDto;
import com.FoodieIndia.Foodie_India.DTO.Subscription.SubscriptionHistoryDto;
import com.FoodieIndia.Foodie_India.Enums.Role;
import com.FoodieIndia.Foodie_India.Exception.AccessDeniedException;
import com.FoodieIndia.Foodie_India.Exception.InvalidSubscritptionDetailsException;
import com.FoodieIndia.Foodie_India.Exception.SubscriptionNotFoundException;
import com.FoodieIndia.Foodie_India.Mapper.mapToSubscriptionFullResponseDto;
import com.FoodieIndia.Foodie_India.Repository.SubscriptionRepository;
import com.FoodieIndia.Foodie_India.model.Subscription;
import com.FoodieIndia.Foodie_India.model.User;
import com.FoodieIndia.Foodie_India.model.UserSubscriptions;
import com.FoodieIndia.Foodie_India.Repository.UserSubscriptionRepository;

@Service
public class SubscriptionService {

    AuthService authService;
    SubscriptionFullResponseDto subscriptionFullResponseDto;
    SubscriptionRepository subscriptionRepository;
    UserSubscriptionRepository UserSubscriptionRepository;
    ResponseService responseService;
    SubscriptionHistoryDto subscriptionHistoryDto;

    SubscriptionService(AuthService authService, SubscriptionRepository subscriptionRepository,
            UserSubscriptionRepository userSubscriptionRepository, ResponseService responseService) {
        this.authService = authService;
        this.responseService = responseService;
        this.subscriptionRepository = subscriptionRepository;
        this.UserSubscriptionRepository = userSubscriptionRepository;
    }

    public boolean isSubscriptionDataValid(SubscriptionDto data) {
        if (data == null
                || data.getSubscription_name() == null || data.getSubscription_name().trim().isEmpty()
                || data.getSubscription_description() == null || data.getSubscription_description().trim().isEmpty()
                || data.getDurationInDays() == null || data.getDurationInDays() < 30
                || data.getSubscription_price() == null || data.getSubscription_price() <= 0) {
            return false;
        }
        return true;
    }

    public ResponseEntity<ApiResponse<?>> createSubscription(SubscriptionDto data) {

        try {

            User user = authService.getLoggedInUserDetails();

            if (user.getRole() != Role.ROLE_SUPER_ADMIN) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            if (!isSubscriptionDataValid(data)) {
                throw new InvalidSubscritptionDetailsException("Please Enter Valid Subscription Details");
            }

            Subscription subscription = new Subscription();
            subscription.setSubscription_name(data.getSubscription_name());
            subscription.setSubscription_description(data.getSubscription_description());
            subscription.setSubscription_price(data.getSubscription_price());
            subscription.setDurationInDays(data.getDurationInDays());
            subscription.setCreatedAt(LocalDateTime.now());
            subscription.setCreatedByUser(user);

            subscriptionRepository.save(subscription);

            subscriptionFullResponseDto = new mapToSubscriptionFullResponseDto().mapToSubscription(subscription);

            return new ResponseEntity<>(
                    responseService.sendResponse(subscriptionFullResponseDto, "Subscription Created Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Create Subscription" + e.getMessage());
            throw new InvalidSubscritptionDetailsException("Failed to Create Subscription " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> getAllSubscriptions() {
        try {

            List<Subscription> subscriptions = subscriptionRepository.findAll();

            List<SubscriptionFullResponseDto> mappedList = new ArrayList<>();

            for (Subscription subscription : subscriptions) {
                subscriptionFullResponseDto = new mapToSubscriptionFullResponseDto().mapToSubscription(subscription);
                mappedList.add(subscriptionFullResponseDto);
            }

            return new ResponseEntity<>(
                    responseService.sendResponse(mappedList, "Subscriptions Fetched Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            System.out.print(e.getMessage());
            throw new InvalidSubscritptionDetailsException("Failed To Fetch the Subscriptions " + e.getMessage());

        }
    }

    public ResponseEntity<ApiResponse<?>> getSubscriptionById(int id) {
        try {

            Subscription subscription = subscriptionRepository.findById(id)
                    .orElseThrow(() -> new SubscriptionNotFoundException("Failed to fetch Subscription with id " + id));

            subscriptionFullResponseDto = new mapToSubscriptionFullResponseDto().mapToSubscription(subscription);

            return ResponseEntity.ok(
                    responseService.sendResponse(subscriptionFullResponseDto, "Subscription fetched successfully",
                            HttpStatus.OK.value()));

        } catch (Exception e) {
            System.out.println("Get Subscription By Id " + e.getMessage());
            throw new InvalidSubscritptionDetailsException(
                    "Something went wrong while fetching Subscription " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> updateSubscription(int id, SubscriptionDto data) {
        try {

            User user = authService.getLoggedInUserDetails();

            if (user.getRole() != Role.ROLE_SUPER_ADMIN) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            Subscription subscription = subscriptionRepository.findById(id)
                    .orElseThrow(() -> new SubscriptionNotFoundException("Failed to fetch Subscription with id " + id));

            subscription.setSubscription_name(data.getSubscription_name());
            subscription.setSubscription_description(data.getSubscription_description());
            subscription.setSubscription_price(data.getSubscription_price());
            subscription.setDurationInDays(data.getDurationInDays());

            Subscription savedSubscription = subscriptionRepository.save(subscription);
            subscriptionFullResponseDto = new mapToSubscriptionFullResponseDto().mapToSubscription(savedSubscription);

            return ResponseEntity.ok(
                    responseService.sendResponse(subscriptionFullResponseDto, "Subscription updated successfully",
                            HttpStatus.OK.value()));

        } catch (Exception e) {
            System.out.println("Get Subscription By Id " + e.getMessage());
            throw new InvalidSubscritptionDetailsException(
                    "Something went wrong while updating Subscription " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> purchaseSubscription(int subscriptionID,
            PurchaseSubscriptionRequestDto data) {
        try {
            User user = authService.getLoggedInUserDetails();

            if (user.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            Subscription subscription = subscriptionRepository.findById(subscriptionID)
                    .orElseThrow(() -> new SubscriptionNotFoundException(
                            "Failed to fetch Subscription with id " + subscriptionID));

            boolean hasActiveSubscription = user.getPurchased_subscriptions().stream()
                    .anyMatch(UserSubscriptions::isActive);

            if (hasActiveSubscription) {
                return new ResponseEntity<>(
                        responseService.sendResponse(null, "User already has an active subscription",
                                HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST);
            }

            // Proceed with new subscription
            UserSubscriptions newSubscription = new UserSubscriptions();
            newSubscription.setUser(user);
            newSubscription.setSubscription(subscription);
            newSubscription.setStartTime(LocalDateTime.now());
            newSubscription.setEndTime(LocalDateTime.now().plusDays(subscription.getDurationInDays()));
            // newSubscription.setEndTime(LocalDateTime.now().plusMinutes(5));
            newSubscription.setActive(true);
            newSubscription.setPurchasedAt(LocalDateTime.now());
            newSubscription.setPayment_id(data.getRazorpay_payment_id());
            newSubscription.setOrder_id(data.getRazorpay_order_id());

            UserSubscriptionRepository.save(newSubscription);

            subscription.getPurchasedByUsers().add(newSubscription);
            subscriptionRepository.save(subscription);

            SubscriptionFullResponseDto subscriptionFullResponseDto = new mapToSubscriptionFullResponseDto()
                    .mapToSubscription(subscription);

            return new ResponseEntity<>(
                    responseService.sendResponse(subscriptionFullResponseDto, "Subscription Purchased Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK);

        } catch (Exception e) {
            throw new InvalidSubscritptionDetailsException("Failed to Purchase Subscription: " + e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> AllpurchaseSubscriptionByUser() {
        try {

            User user = authService.getLoggedInUserDetails();

            System.out.println(user.getEmail());
            System.out.println(user.getRole());

            List<UserSubscriptions> subscriptions;
            List<SubscriptionHistoryDto> mappedList = new ArrayList<>();
            if (user.getRole() == Role.ROLE_USER) {
                throw new AccessDeniedException("You are not authorized to access this resource.");
            }

            if (user.getRole() == Role.ROLE_SUPER_ADMIN) {
                subscriptions = UserSubscriptionRepository.findAll();
            } else {
                subscriptions = UserSubscriptionRepository.findByuser(user);

            }

            if (subscriptions.isEmpty()) {
                return ResponseEntity.ok(
                        responseService.sendResponse(mappedList, "Subscriptions fetched successfully",
                                HttpStatus.OK.value()));
            }

            for (UserSubscriptions subscription : subscriptions) {

                subscriptionHistoryDto = new SubscriptionHistoryDto();

                subscriptionHistoryDto.setId(subscription.getId());
                subscriptionHistoryDto.setUser_id(subscription.getUser().getUserId());
                subscriptionHistoryDto.setSubscription_id(subscription.getSubscription().getSubscription_id());
                subscriptionHistoryDto.setPayment_id(subscription.getPayment_id());
                subscriptionHistoryDto.setOrder_id(subscription.getOrder_id());
                subscriptionHistoryDto.setPurchasedAt(subscription.getPurchasedAt());
                subscriptionHistoryDto.setStartTime(subscription.getStartTime());
                subscriptionHistoryDto.setEndTime(subscription.getEndTime());
                subscriptionHistoryDto.setActive(subscription.isActive());

                mappedList.add(subscriptionHistoryDto);
            }

            return ResponseEntity.ok(
                    responseService.sendResponse(mappedList, "Subscriptions fetched successfully",
                            HttpStatus.OK.value()));

        } catch (Exception e) {
            System.out.println("Get Subscription By Id Error: " + e.getMessage());
            throw new InvalidSubscritptionDetailsException(
                    "Something went wrong while fetching subscriptions: " + e.getMessage());
        }
    }

    public boolean getSubscriptionStatusofUser() {

        User user = authService.getLoggedInUserDetails();

        boolean hasActiveSubscription = user.getPurchased_subscriptions().stream()
                .anyMatch(UserSubscriptions::isActive);

        if (hasActiveSubscription)
            return true;
        else
            return false;
    }

}
