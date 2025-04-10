package com.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.demo.model.Airports;
import com.demo.model.Flight;
import com.demo.model.DTO.FlightDTO;

public interface FlightService {
	List<FlightDTO> findAllFlight(); // 查詢所有航班
    Optional<FlightDTO> findFlightById(int id); // 根據 ID 查詢航班
    ResponseEntity<Flight> createFlight(FlightDTO flight); // 新增航班
    Flight updateFlight(int id, FlightDTO flightDetails); // 更新航班
    void deleteFlight(int id); // 刪除航班
    Page<FlightDTO> searchFlights(String originAirport, String destinationAirport, Date startTime,
    Date endTime, String airplaneModelName, Pageable pageable) ;
	
}
