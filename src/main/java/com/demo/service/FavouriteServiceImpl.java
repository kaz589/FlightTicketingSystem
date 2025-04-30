package com.demo.service;

import com.demo.dto.CreateFavouriteRequest;
import com.demo.dto.FavouriteResponse;
import com.demo.entity.Favourite;
import com.demo.repository.FavouriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    @Override
    public List<FavouriteResponse> getFavouriteByMemberId(Integer memberId) {
        List<Favourite> favourites = favouriteRepository.findByMemberId(memberId);
        if (favourites.isEmpty()) {
            throw new RuntimeException("No favourite found for member id " + memberId);
        }

        return favourites.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FavouriteResponse createFavourite(CreateFavouriteRequest request) {
        Favourite favourite = new Favourite();
        favourite.setMemberId(request.getMemberId());
        favourite.setCityId(request.getCityId());
        favourite.setCreatedAt(request.getCreatedAt());

        return mapToResponse(favouriteRepository.save(favourite));

    }

    @Override
    public void deleteFavourite(Long id) {
        favouriteRepository.deleteById(id);
    }

    private FavouriteResponse mapToResponse(Favourite favourite) {
        FavouriteResponse response = new FavouriteResponse();
        response.setId(favourite.getId());
        response.setMemberId(favourite.getMemberId());
        response.setCityId(favourite.getCityId());
        response.setCreatedAt(favourite.getCreatedAt());
        return response;
    }
}
