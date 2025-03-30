package com.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;


import com.demo.model.Airports;

public interface AirportsService {
	
	
	List<Airports> findAllAirports();
	//分頁查詢
	 Page<Airports> getPagedMessages(int page, int size);
}
