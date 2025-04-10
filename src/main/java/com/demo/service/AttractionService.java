package com.demo.service;

import java.util.List;

import com.demo.dto.AttractionResponse;
import com.demo.dto.CreateAttractionRequest;
import com.demo.dto.UpdateAttractionRequest;

public interface AttractionService {

    AttractionResponse createAttraction(CreateAttractionRequest request);

    AttractionResponse updateAttraction(Long id, UpdateAttractionRequest request);

    void deleteAttraction(Long id);

    List<AttractionResponse> getAttractionByName(String name);

    List<AttractionResponse> getAllAttractionsByCityId(Long cityId);
}
