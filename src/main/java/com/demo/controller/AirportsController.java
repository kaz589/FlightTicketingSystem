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
import org.springframework.web.bind.annotation.PutMapping;
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
	@GetMapping("/distinct-AirportName")
	public List<String> getDistinctAirportName() {
		return airportsservice.getDistinctAirportName();
	}
	
	// 搜索接口
    @GetMapping("/search")
	    public Page<Airports> searchAirports(
	    		  @RequestParam(required = false, defaultValue = "all") String keyword,
	    	        @RequestParam(required = false, defaultValue = "all") String city,
	    	        @RequestParam(required = false, defaultValue = "all") String countryRegion,
	    	        @RequestParam(required = false, defaultValue = "1") int page,
	    	        @RequestParam(required = false, defaultValue = "20") int size,
	    	        @RequestParam(required = false, defaultValue = "airportsId") String sortBy,
	    	        @RequestParam(required = false, defaultValue = "asc") String sortOrder
	    ) {
	        return airportsservice.searchAirports(keyword, city, countryRegion, page, size, sortBy, sortOrder);
	    }
	 
	  /**
	     * 新增機場
	     * @param airport 機場實體
	     * @return 新增後的機場實體
	     */
	    @PostMapping
	    public ResponseEntity<Airports> addAirport(@RequestBody Airports airport ) {

	    	System.out.println("AAAA");
	    	System.out.println(airport.getIataCode());

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
	    /**
	     * 根據 ID 更新機場
	     * 
	     * @param airportId 機場 ID
	     * @param updatedAirport 更新後的機場實體
	     * @return 更新後的機場實體
	     */
	    @PutMapping("/{id}")
	    public ResponseEntity<Airports> updateAirport(
	            @PathVariable("id") Integer airportId,
	            @RequestBody Airports updatedAirport) {
	        // 確保 ID 一致，防止數據不匹配
	        if (!airportId.equals(updatedAirport.getAirportsId())) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "機場 ID 不一致");
	        }

	        // 調用服務層更新方法
	        Airports updated = airportsservice.updateAirport(airportId, updatedAirport);

	        return new ResponseEntity<>(updated, HttpStatus.OK);
	    }

}
