package com.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Airports;

public interface AirportsRepository extends JpaRepository<Airports, Integer> {

	@Query("SELECT DISTINCT a.city FROM Airports a WHERE a.city IS NOT NULL")
	List<String> findDistinctByCityNotNull();
	
	


	@Query("SELECT DISTINCT a.airportName FROM Airports a WHERE a.airportName IS NOT NULL")
	List<String> findDistinctByAirportNameNotNull();
	
	@Query("SELECT DISTINCT a.countryRegion FROM Airports a WHERE a.countryRegion IS NOT NULL")
	List<String> findDistinctByCountryRegionNotNull();
	
	//多條件搜尋
	@Query("FROM Airports a WHERE " +
		       "(:keyword IS NULL OR a.airportName LIKE %:keyword% OR a.iataCode LIKE %:keyword%) AND " +
		       "(:city IS NULL OR a.city = :city) AND " +
		       "(:countryRegion IS NULL OR a.countryRegion = :countryRegion)")
	    Page<Airports> searchAirports(@Param("keyword") String keyword,
	                                  @Param("city") String city,
	                                  @Param("countryRegion") String countryRegion,
	                                  Pageable pageable);
	
	// 自訂查詢方法，根據 IATA 代碼查找機場
    Airports findByIataCode(String iataCode);
    
    Airports findByAirportName(String AirportName);
	

}
