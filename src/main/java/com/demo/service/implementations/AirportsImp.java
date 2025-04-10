package com.demo.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	// 獲取所有不重複且不為 null 的機場名
	@Override
	public List<String> getDistinctAirportName() {
		// TODO Auto-generated method stub
		return airportsRepo.findDistinctByAirportNameNotNull();
	}

	@Override
	public Page<Airports> searchAirports(String keyword, String city, String countryRegion, int page, int size,String sortBy,String sortOrder) {
		 
		keyword=(keyword==null||keyword.trim().isEmpty()||keyword.trim().equalsIgnoreCase("all"))?null:keyword;
		city=(city==null||city.trim().isEmpty()||city.trim().equalsIgnoreCase("all"))?null:city;
		countryRegion=(countryRegion==null||countryRegion.trim().isEmpty()||countryRegion.trim().equalsIgnoreCase("all"))?null:countryRegion;
	
		// 處理排序邏輯
        Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		    
		PageRequest  pageable = PageRequest.of(page-1, size,sort);
		//Pageable pageable = PageRequest.of(0, 10); // 第0頁，每頁10筆資料
	    return airportsRepo.searchAirports(keyword, city,countryRegion,pageable);
	}

	  @Override
	    public Airports addAirport(Airports airport) {
	        return airportsRepo.save(airport);
	    }

	    @Override
	    public void deleteAirportById(Integer airportId) {
	    	airportsRepo.deleteById(airportId);
	    }

		@Override
		public Airports updateAirport(Integer airportId, Airports updatedAirport) {
			// 查找機場是否存在
			Airports existingAirport = airportsRepo.findById(airportId)
			        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "機場未找到"));
		   

		    // 更新機場的屬性
		    existingAirport.setAirportName(updatedAirport.getAirportName());
		    existingAirport.setCity(updatedAirport.getCity());
		    existingAirport.setCountryRegion(updatedAirport.getCountryRegion());
		    existingAirport.setIataCode(updatedAirport.getIataCode());
		    // 根據需要更新其他字段

		    // 保存更新後的機場
		    return airportsRepo.save(existingAirport);
		}

		
}
