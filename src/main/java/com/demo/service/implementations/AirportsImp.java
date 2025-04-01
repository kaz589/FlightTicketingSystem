package com.demo.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.model.Airports;
import com.demo.repository.AirportsRepository;
import com.demo.service.AirportsService;

@Service
public class AirportsImp implements AirportsService {

	@Autowired
	private AirportsRepository airportsRepo;

	@Override
	public List<Airports> findAllAirports() {
		List<Airports> allairports = airportsRepo.findAll();

		return allairports;
	}

	@Override
	public Page<Airports> getPagedMessages(int page, int size) {
		// 使用 PageRequest 創建分頁參數，並指定排序
		Page<Airports> pageResult = airportsRepo.findAll(
				PageRequest.of(page, // 查詢的頁數，從0起算
						size, // 查詢的每頁筆數
						Sort.by("airportsId"))); // 依 airportsId 欄位降冪排序

		// 直接返回 pageResult
		return pageResult;
	}
	// 獲取所有不重複且不為 null 的城市名稱

	@Override
	public List<String> getDistinctCities() {

		return airportsRepo.findDistinctByCityNotNull();
	}

	// 獲取所有不重複且不為 null 的國家/地區名稱

	@Override
	public List<String> getDistinctCountryRegions() {

		return airportsRepo.findDistinctByCountryRegionNotNull();
	}

	@Override
	public Page<Airports> searchAirports(String keyword, String city, String countryRegion, int page, int size) {
		  System.out.println(keyword);
		    System.out.println(city);
		    System.out.println(countryRegion);
		keyword=(keyword==null||keyword.trim().isEmpty()||keyword.trim().equalsIgnoreCase("all"))?null:keyword;
		city=(city==null||city.trim().isEmpty()||city.trim().equalsIgnoreCase("all"))?null:city;
		countryRegion=(countryRegion==null||countryRegion.trim().isEmpty()||countryRegion.trim().equalsIgnoreCase("all"))?null:countryRegion;
		
		
		    System.out.println(keyword);
		    System.out.println(city);
		    System.out.println(countryRegion);
		PageRequest pageable = PageRequest.of(page, size);
	    return airportsRepo.searchAirports(keyword, city, countryRegion, pageable);
	}

	  @Override
	    public Airports addAirport(Airports airport) {
	        return airportsRepo.save(airport);
	    }

	    @Override
	    public void deleteAirportById(Integer airportId) {
	    	airportsRepo.deleteById(airportId);
	    }

}
