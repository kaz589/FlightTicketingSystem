package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.model.Airports;
import com.demo.service.AirportsService;

import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("/api")
public class AirportsController {
	@Autowired
	private AirportsService airportsservice;
	
	@GetMapping("/getAll")
	@Operation(summary = "Get all books", description = "Retrieves a list of all books")
	public  List<Airports> sayHello() {
		
			List<Airports> allairports =airportsservice.findAllAirports();
			return allairports;
	}
	
	
	 @GetMapping("/Test/{page}/{size}")
	    public Page<Airports> TestAPI(
	    	    @PathVariable(value = "page") int page,
	    	    @PathVariable(value = "size") int size) {
	        // 獲取分頁數據
	        Page<Airports> allairports = airportsservice.getPagedMessages(page, size);

	        // 如果資源為空，拋出異常
	        if (allairports == null || allairports.isEmpty()) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "資源未找到");
	        }

	        return allairports;
	    }

}
