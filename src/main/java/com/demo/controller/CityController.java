package com.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.dto.CityResponse;
import com.demo.dto.CreateCityRequest;
import com.demo.dto.UpdateCityRequest;
import com.demo.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<CityResponse> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/id/{id}")
    public CityResponse getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @GetMapping("/city/{name}")
    public List<CityResponse> getCityByName(@PathVariable String name) {
        return cityService.getCityByName(name);
    }

    @PostMapping
    public CityResponse createCity(@RequestBody CreateCityRequest request) {
        return cityService.createCity(request);
    }

    @PostMapping("/imageUrl/{id}")
    public ResponseEntity<CityResponse> updateCityImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile imageFile) {

        try {
            String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(imageFile.getOriginalFilename());

            Path uploadPath = Paths.get("uploads/images");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            UpdateCityRequest updateRequest = new UpdateCityRequest();
            updateRequest.setImage("/images/" + fileName);
            CityResponse updatedCity = cityService.updateCityImage(id, updateRequest);
            return ResponseEntity.ok(updatedCity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
