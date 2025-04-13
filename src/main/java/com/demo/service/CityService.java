package com.demo.service;

import java.util.List;

import com.demo.dto.CityResponse;
import com.demo.dto.CreateCityRequest;
import com.demo.dto.UpdateCityRequest;

public interface CityService {

    List<CityResponse> getAllCities();

    CityResponse getCityById(Long id);

    List<CityResponse> getCityByName(String name);

    CityResponse createCity(CreateCityRequest request);

    CityResponse updateCity(Long id, UpdateCityRequest request);

    void deleteCity(Long id);
}
