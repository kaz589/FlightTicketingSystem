package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Favourite;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    List<Favourite> findByMemberId(Integer memberId);
}
