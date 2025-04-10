package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.model.AirplaneModel;
import com.demo.model.Airports;
import java.util.List;
import java.util.Optional;


public interface AirplaneModelRepository extends JpaRepository<AirplaneModel, Integer> {
	
	Optional<AirplaneModel>  findByModelName(String modelName);
	
	@Query("SELECT DISTINCT a.modelName FROM AirplaneModel a WHERE a.modelName IS NOT NULL")
	List<String> findDistinctBymodelNameNotNull();

}
