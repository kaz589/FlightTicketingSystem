package com.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(
            @PathVariable Long id,
            @RequestBody UpdateCityRequest request) {
        CityResponse updatedCity = cityService.updateCity(id, request);
        return ResponseEntity.ok(updatedCity);
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile file)
            throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Empty file"));
        }

        File dir = new File("uploads/images/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filename = file.getOriginalFilename();
        Path filepath = Paths.get("uploads/images/", filename);
        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        String imageUrl = "/images/" + filename;

        return ResponseEntity.ok(Map.of("url", imageUrl));
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
