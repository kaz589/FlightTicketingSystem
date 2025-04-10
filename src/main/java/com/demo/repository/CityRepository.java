package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByNameContaining(String name);

}
