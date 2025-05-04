package com.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.CreateFavouriteRequest;
import com.demo.dto.FavouriteResponse;
import com.demo.service.FavouriteService;

import java.util.List;

@RestController
@RequestMapping("/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<FavouriteResponse>> getFavouriteByMemberId(@PathVariable Integer memberId) {
        return ResponseEntity.ok(favouriteService.getFavouriteByMemberId(memberId));
    }

    @PostMapping
    public ResponseEntity<FavouriteResponse> createFavourite(@RequestBody CreateFavouriteRequest request) {
        return ResponseEntity.ok(favouriteService.createFavourite(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable Long id) {
        favouriteService.deleteFavourite(id);
        return ResponseEntity.noContent().build();}
}
