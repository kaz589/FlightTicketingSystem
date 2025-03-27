package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Airports;

public interface AirportsRepository extends JpaRepository<Airports, Integer> {

}
