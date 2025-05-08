package com.demo.service;

import com.demo.dto.CreateFavouriteRequest;
import com.demo.dto.FavouriteResponse;

import java.util.List;

public interface FavouriteService {

    List<FavouriteResponse> getFavouriteByMemberId(Integer memberId);

    FavouriteResponse createFavourite(CreateFavouriteRequest request);

    void deleteFavourite(Long id);
}
