package com.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;


import com.demo.model.Airports;

public interface AirportsService {
	
	
	  List<String> getDistinctAirportName();
	List<Airports> findAllAirports();
	//分頁查詢
	 Page<Airports> getPagedMessages(int page, int size);
	 
	  /**
	     * 獲取所有不重複且不為 null 的城市名稱
	     * @return 不重複的城市名稱列表
	     */
	   List<String> getDistinctCities() ;
	   /**
	     * 獲取所有不重複且不為 null 的國家/地區名稱
	     * @return 不重複的國家/地區名稱列表
	     */
	   List<String> getDistinctCountryRegions();
	   
	   Page<Airports> searchAirports(String keyword, String city, String countryRegion, int page, int size,String sortBy,String sortOrder);
	   /**
	     * 新增機場
	     * @param airport 機場實體
	     * @return 新增後的機場實體
	     */
	    Airports addAirport(Airports airport);

	    /**
	     * 根據 ID 刪除機場
	     * @param airportId 機場 ID
	     */
	    void deleteAirportById(Integer airportId);
	    /**
	     * 根據 ID 更新機場
	     * @param airportId 機場 ID
	     */
	    Airports updateAirport(Integer airportId, Airports updatedAirport);
	 
}
