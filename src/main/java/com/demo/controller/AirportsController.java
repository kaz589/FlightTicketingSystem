package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.model.Airports;
import com.demo.service.spec.IAirports;

import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("/api")
public class AirportsController {
	@Autowired
	private IAirports airportsservice;
	
	@GetMapping("/getAll")
	@Operation(summary = "Get all books", description = "Retrieves a list of all books")
	public  List<Airports> sayHello() {
		
			List<Airports> allairports =airportsservice.findAllAirports();
			return allairports;
	}
	@GetMapping("/Test")
	@Operation(summary = "Get all books", description = "Retrieves a list of all books")
	public  List<Airports> TestAPI() {
		
			//List<Airports> allairports =airportsservice.findAllAirports();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "資源未找到");
	}
}
