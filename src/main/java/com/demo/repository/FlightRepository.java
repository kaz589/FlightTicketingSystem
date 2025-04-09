package com.demo.repository;

import java.util.Date;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Airports;
import com.demo.model.Flight;
import com.demo.model.DTO.FlightDTO;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
	
	@Query("SELECT f FROM Flight f WHERE " +
	           "(:originAirport IS NULL OR f.originAirport.airportName = :originAirport) AND " +
	           "(:destinationAirport IS NULL OR f.destinationAirport.airportName = :destinationAirport) AND " +
	           "(:startTime IS NULL OR f.departureTime >= :startTime) AND " +
	           "(:endTime IS NULL OR f.departureTime <= :endTime) AND " +
	           "(:airplaneModelName IS NULL OR f.airplaneModel.modelName = :airplaneModelName)")
	    Page<FlightDTO> searchFlights(
	            @Param("originAirport") String originAirport,
	            @Param("destinationAirport") String destinationAirport,
	            @Param("startTime") Date startTime,
	            @Param("endTime") Date endTime,
	            @Param("airplaneModelName") String airplaneModelName,
	            Pageable pageable
	    );

}
