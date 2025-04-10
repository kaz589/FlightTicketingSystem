package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	@Operation(summary = "Get all books", description = "Retrieves a list of all books")
	public List<SeatDTO> sayHello() {

		List<SeatDTO> allairports = seatService.findAllSeat();
		return allairports;
	}

}
