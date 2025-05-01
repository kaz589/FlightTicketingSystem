package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	
	@GetMapping("/tickets/{ticketId}")
	public List<SeatDTO> getSeatsByTicketId(@PathVariable Integer ticketId) {
	    return seatService.findSeatsByTicketId(ticketId);
	}
	 @PostMapping("/release/{seatId}")
	    public ResponseEntity<String> releaseSeat(@PathVariable Integer seatId) {
	        seatService.releaseSeatById(seatId);
	        return ResponseEntity.ok("座位已解除綁定");
	 }
	 
	 @GetMapping("/search/flights/{flightId}/{status}")
	 public List<SeatDTO> getSeatsByFlightIdAndStatus(
	     @PathVariable Integer flightId,
	     @PathVariable String status
	 ) {
		 
		 // 查全部座位
		    if (flightId == 0 && "all".equalsIgnoreCase(status)) {
		        return seatService.findAllSeat();
		    }
		    // 查所有航班的已預定/未預定
		    if (flightId == 0 && ("true".equalsIgnoreCase(status) || "false".equalsIgnoreCase(status))) {
		        boolean booked = Boolean.parseBoolean(status);
		        return seatService.findSeatsByBooked(booked);
		    }
		    // 查某航班全部座位
		    if (flightId != 0 && "all".equalsIgnoreCase(status)) {
		        return seatService.findSeatsByFlightId(flightId);
		    }
		    // 查某航班已預定/未預定
		    if (flightId != 0 && ("true".equalsIgnoreCase(status) || "false".equalsIgnoreCase(status))) {
		        boolean booked = Boolean.parseBoolean(status);
		        return seatService.findSeatsByFlightIdAndBooked(flightId, booked);
		    }
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "狀態參數錯誤");
	 }
	 

}
