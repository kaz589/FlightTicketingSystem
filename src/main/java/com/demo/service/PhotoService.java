package com.demo.service;

import java.util.List;

import com.demo.dto.CreatePhotoRequest;
import com.demo.dto.PhotoResponse;
import com.demo.dto.UpdatePhotoRequest;

public interface PhotoService {
    PhotoResponse createPhoto(CreatePhotoRequest request);

    PhotoResponse updatePhoto(Long id, UpdatePhotoRequest request);

    void deletePhoto(Long id);

    List<PhotoResponse> getPhotosByAttractionId(Long attractionID);

}
