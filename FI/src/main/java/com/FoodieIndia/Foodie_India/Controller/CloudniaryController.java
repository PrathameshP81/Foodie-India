package com.FoodieIndia.Foodie_India.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.FoodieIndia.Foodie_India.Service.CloudniaryService;

@RestController
@RequestMapping("/cloudniary")
public class CloudniaryController {

    @Autowired
    CloudniaryService cloudniaryService;

    @PostMapping()
    public Map<?, ?> updaloadDataOnCloudiary(@RequestPart MultipartFile file, @RequestPart Object data)
            throws Exception {
        System.out.println(data);
        return cloudniaryService.uploadFile(file);
    }
}
