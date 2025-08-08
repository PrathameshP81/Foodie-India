package com.FoodieIndia.Foodie_India.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FoodieIndia.Foodie_India.DTO.Subscription.PurchaseSubscriptionRequestDto;
import com.FoodieIndia.Foodie_India.DTO.Subscription.SubscriptionDto;
import com.FoodieIndia.Foodie_India.Service.SubscriptionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    SubscriptionService subscriptionService;

    @PostMapping()
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionDto data) {
        return subscriptionService.createSubscription(data);
    }

    @GetMapping()
    public ResponseEntity<?> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubscriptionByGivenId(@PathVariable("id") int id) {
        return subscriptionService.getSubscriptionById(id);
    }

    @GetMapping("/user/purchasedSub")
    public ResponseEntity<?> getAllPurchaseSubscriptionByGivenUser() {
        return subscriptionService.AllpurchaseSubscriptionByUser();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateSubscriptionByGivenId(@PathVariable("id") int id,
            @RequestBody SubscriptionDto data) {
        return subscriptionService.updateSubscription(id, data);
    }

    // Purchase Subscription
    @PostMapping("/{id}")
    public ResponseEntity<?> purchaseGivenSubscription(@PathVariable("id") int id,
            @RequestBody PurchaseSubscriptionRequestDto data) {
        return subscriptionService.purchaseSubscription(id, data);
    }

}
