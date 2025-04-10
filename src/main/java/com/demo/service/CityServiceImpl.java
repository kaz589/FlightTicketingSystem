package com.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.dto.CityResponse;
import com.demo.dto.CreateCityRequest;
import com.demo.dto.UpdateCityRequest;
import com.demo.entity.City;
import com.demo.repository.CityRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<CityResponse> getAllCities() {
        return cityRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CityResponse getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
        return mapToResponse(city);
    }

    @Override
    public List<CityResponse> getCityByName(String name) {
        List<City> city = cityRepository.findByNameContaining(name);
        if (city.isEmpty()) {
            throw new RuntimeException("No cities found");
        }

        return city.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CityResponse createCity(CreateCityRequest request) {
        City city = new City();
        city.setName(request.getName());
        city.setCountry(request.getCountry());
        city.setLatitude(request.getLatitude());
        city.setLongitude(request.getLongitude());
        city.setImage(request.getImage());

        return mapToResponse(cityRepository.save(city));
    }

    @Override
    public CityResponse updateCityImage(Long id, UpdateCityRequest request) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        city.setImage(request.getImage());

        return mapToResponse(cityRepository.save(city));
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    private CityResponse mapToResponse(City city) {
        CityResponse response = new CityResponse();
        response.setId(city.getId());
        response.setName(city.getName());
        response.setCountry(city.getCountry());
        response.setLatitude(city.getLatitude());
        response.setLongitude(city.getLongitude());
        response.setImage(city.getImage());

        return response;
    }
}
