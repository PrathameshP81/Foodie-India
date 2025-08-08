package com.FoodieIndia.Foodie_India.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.FoodieIndia.Foodie_India.DTO.Recepie.RecepieDto;
import com.FoodieIndia.Foodie_India.Service.RecepieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recepie")
public class RecepieController {

    @Autowired
    RecepieService recepieService;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createRecepie(@RequestPart List<MultipartFile> file,
            @Valid @RequestPart RecepieDto data) {
        return recepieService.createRecepie(file, data);
    }

    @GetMapping()
    public ResponseEntity<?> getAllRecepies() {
        return recepieService.getRecepies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecepieByGivenId(@PathVariable("id") int id) {
        return recepieService.getRecepieById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteRecepieByGivenId(@PathVariable("id") int id) {
        return recepieService.deleteRecpieById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecepie(@PathVariable("id") int id, @RequestPart List<MultipartFile> file,
            @Valid @RequestPart RecepieDto data) {
        return recepieService.updateRecepie(id, file, data);
    }

}
