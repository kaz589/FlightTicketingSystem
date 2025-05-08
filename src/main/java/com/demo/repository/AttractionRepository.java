package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    List<Attraction> findByCityId(Long acityId);

    List<Attraction> findByNameContainingIgnoreCaseOrCity_NameContainingIgnoreCase(String name, String cityName);

}
