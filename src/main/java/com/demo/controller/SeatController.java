package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Seat;
import com.demo.model.DTO.SeatDTO;
import com.demo.service.SeatService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/seat")
public class SeatController {
	
	@Autowired
	private SeatService seatService;
	
	
	@GetMapping("/getAll")
	public List<SeatDTO> getAllSeats() {

		List<SeatDTO> allairports = seatService.findAllSeat();
		return allairports;
	}
	
	@GetMapping("/flights/{flightId}")
	public List<SeatDTO> getSeatsByFlightId(@PathVariable Integer flightId) {
		return seatService.findSeatsByFlightId(flightId);
	}

}
