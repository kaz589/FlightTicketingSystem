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

import com.demo.dto.CreatePhotoRequest;
import com.demo.dto.PhotoResponse;
import com.demo.dto.UpdatePhotoRequest;
import com.demo.service.PhotoService;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<PhotoResponse> createPhoto(@RequestBody CreatePhotoRequest request) {
        return ResponseEntity.ok(photoService.createPhoto(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhotoResponse> updatePhoto(@PathVariable Long id,
            @RequestBody UpdatePhotoRequest request) {
        return ResponseEntity.ok(photoService.updatePhoto(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
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

    @GetMapping("/attraction/{attractionId}")
    public ResponseEntity<List<PhotoResponse>> getPhotosByAttractionId(@PathVariable Long attractionId) {
        List<PhotoResponse> responses = photoService.getPhotosByAttractionId(attractionId);
        return ResponseEntity.ok(responses);
    }
}