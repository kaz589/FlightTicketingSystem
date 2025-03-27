package com.demo.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Airports;
import com.demo.repository.AirportsRepository;
import com.demo.service.spec.IAirports;

@Service
public class AirportsService  implements IAirports{

	@Autowired
	private AirportsRepository airportsRepo;
	
	@Override
	public List<Airports> findAllAirports() {
		    List<Airports> allairports   =airportsRepo.findAll();
		
		return allairports;
	}

}
