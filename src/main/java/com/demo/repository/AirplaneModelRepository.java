package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.AirplaneModel;
import com.demo.model.Airports;
import java.util.List;
import java.util.Optional;


public interface AirplaneModelRepository extends JpaRepository<AirplaneModel, Integer> {
	
	Optional<AirplaneModel>  findByModelName(String modelName);

}
