package com.FoodieIndia.Foodie_India.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.FoodieIndia.Foodie_India.DTO.Plan.PlanDto;
import com.FoodieIndia.Foodie_India.Service.PlanService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    PlanService planService;

    @GetMapping()
    public ResponseEntity<?> getPlans() {
        return planService.getAllPlans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlanByGivenId(@PathVariable("id") int id) {
        return planService.getPlanById(id);
    }

    @GetMapping("/userPurchasedPlans")
    public ResponseEntity<?> getAllPurchasedPlansByLoggedInUser() {
        return planService.getAllPurchasedPlansByUser();
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createPlan(@RequestPart("file") MultipartFile file, @RequestPart("data") PlanDto data) {
        return planService.createPlan(file, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlanByGivenID(@PathVariable("id") int id) {
        return planService.deletePlanById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlanByGivenID(
            @PathVariable("id") int id,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("data") PlanDto data) {

        System.out.println("Updated Plan Data" + data);
        return planService.updatePlanById(id, file, data);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> purchasePlan(@PathVariable("id") int id) {
        return planService.purchasePlan(id);
    }

}
