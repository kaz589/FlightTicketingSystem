package com.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.dto.AttractionResponse;
import com.demo.dto.CityResponse;
import com.demo.dto.CreateAttractionRequest;
import com.demo.dto.UpdateAttractionRequest;
import com.demo.entity.Attraction;
import com.demo.entity.City;
import com.demo.repository.AttractionRepository;
import com.demo.repository.CityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {
    // Implement the methods from the AttractionService interface here

    private final AttractionRepository attractionRepository;
    private final CityRepository cityRepository;

    @Override
    public Page<AttractionResponse> getAttractionsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return attractionRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public AttractionResponse createAttraction(CreateAttractionRequest request) {
        Attraction attraction = new Attraction();
        attraction.setName(request.getName());
        attraction.setCategory(request.getCategory());
        attraction.setDescription(request.getDescription());
        attraction.setAddress(request.getAddress());
        attraction.setLatitude(request.getLatitude());
        attraction.setLongitude(request.getLongitude());
        attraction.setRating(request.getRating());

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));
        attraction.setCity(city);

        Attraction saved = attractionRepository.save(attraction);
        return mapToResponse(saved);
    }

    @Override
    public AttractionResponse updateAttraction(Long id, UpdateAttractionRequest request) {
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attraction not found"));

        attraction.setName(request.getName());
        attraction.setCategory(request.getCategory());
        attraction.setDescription(request.getDescription());
        attraction.setAddress(request.getAddress());
        attraction.setLatitude(request.getLatitude());
        attraction.setLongitude(request.getLongitude());
        attraction.setRating(request.getRating());

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));
        attraction.setCity(city);

        return mapToResponse(attractionRepository.save(attraction));

    }

    @Override
    public void deleteAttraction(Long id) {
        attractionRepository.deleteById(id);
    }

    @Override
    public List<AttractionResponse> getAttractionByName(String name) {
        List<Attraction> attraction = attractionRepository.findByNameContainingIgnoreCaseOrCity_NameContainingIgnoreCase(name, name);
        if (attraction.isEmpty()) {
            throw new RuntimeException("No attractions found");
        }

        return attraction.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttractionResponse> getAllAttractionsByCityId(Long cityId) {
        return attractionRepository.findByCityId(cityId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AttractionResponse mapToResponse(Attraction attraction) {
        AttractionResponse response = new AttractionResponse();
        response.setId(attraction.getId());
        response.setName(attraction.getName());
        response.setCategory(attraction.getCategory());
        response.setDescription(attraction.getDescription());
        response.setAddress(attraction.getAddress());
        response.setLatitude(attraction.getLatitude());
        response.setLongitude(attraction.getLongitude());
        response.setRating(attraction.getRating());
        response.setCity(attraction.getCity().getName());
        return response;
    }

}
