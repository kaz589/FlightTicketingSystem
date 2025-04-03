package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.model.Airports;
import com.demo.service.AirportsService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api/airports")
public class AirportsController {
	@Autowired
	private AirportsService airportsservice;

	@GetMapping("/getAll")
	@Operation(summary = "Get all books", description = "Retrieves a list of all books")
	public List<Airports> sayHello() {

		List<Airports> allairports = airportsservice.findAllAirports();
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

	/**
	 * 獲取所有不重複的城市名稱
	 * 
	 * @return 不重複的城市名稱列表
	 */
	@GetMapping("/distinct-cities")
	public List<String> getDistinctCities() {
		return airportsservice.getDistinctCities();
	}

	/**
	 * 獲取所有不重複的國家/地區名稱
	 * 
	 * @return 不重複的國家/地區名稱列表
	 */
	@GetMapping("/distinct-country-regions")
	public List<String> getDistinctCountryRegions() {
		return airportsservice.getDistinctCountryRegions();
	}
	//搜尋機場
	 @GetMapping("/search/{keyword}/{city}/{countryRegion}/{page}/{size}")
	    public Page<Airports> searchAirports(
	            @PathVariable(required = false) String keyword,
	            @PathVariable(required = false) String city,
	            @PathVariable(required = false) String countryRegion,
	            @PathVariable int page,
	            @PathVariable int size
	    ) {
	        return airportsservice.searchAirports(keyword, city, countryRegion, page, size);
	    }
	 
	  /**
	     * 新增機場
	     * @param airport 機場實體
	     * @return 新增後的機場實體
	     */
	    @PostMapping
	    public ResponseEntity<Airports> addAirport(@RequestBody Airports airport ) {
	    	System.out.println("AAAA");
//	    	System.out.println(airport.getIataCode());
	        Airports createdAirport = airportsservice.addAirport(airport);
	        return new ResponseEntity<>(createdAirport, HttpStatus.OK);
	    }

	    /**
	     * 根據 ID 刪除機場
	     * @param airportId 機場 ID
	     * @return 刪除結果
	     */
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteAirport(@PathVariable("id") Integer airportId) {
	    	airportsservice.deleteAirportById(airportId);
	        return new ResponseEntity<>(HttpStatus.OK);
	    }

}
