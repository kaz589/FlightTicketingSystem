package com.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.demo.model.Airports;
import com.demo.model.Flight;
import com.demo.model.DTO.FlightDTO;
import com.demo.repository.AirportsRepository;
import com.demo.service.AirportsService;
import com.demo.service.FlightService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

	@Autowired
	private FlightService flightService;

	@GetMapping("/getAll")
	@Operation(summary = "Get all books", description = "Retrieves a list of all books")
	public List<FlightDTO> sayHello() {

		List<FlightDTO> allairports = flightService.findAllFlight();
		return allairports;
	}
	
	@PutMapping("/{id}")
	public Flight updateFlight(@PathVariable Integer id, @RequestBody FlightDTO flightDetails) {
		
		 // 確保 ID 一致，防止數據不匹配
        if (!id.equals(flightDetails.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "航班 ID 不一致");
        }
	    return flightService.updateFlight(id, flightDetails);
	}
	//刪除
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable int id) {
        flightService.deleteFlight(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	//新增
	@PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody FlightDTO flight) {
        return flightService.createFlight(flight);
    }
	
	//搜尋
	@GetMapping("/search")
    public Page<FlightDTO> searchFlights(
            @RequestParam(required = false) String originIata,
            @RequestParam(required = false) String destinationIata,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(required = false) String airplaneModelName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

       

        Pageable  pageable = PageRequest.of(page-1, size);
        return flightService.searchFlights(originIata, destinationIata, startTime, endTime, airplaneModelName, pageable);
	}
}
