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
public class AirportsImp  implements AirportsService{

	@Autowired
	private AirportsRepository airportsRepo;
	
	@Override
	public List<Airports> findAllAirports() {
		    List<Airports> allairports   =airportsRepo.findAll();
		
		return allairports;
	}

	@Override
	public Page<Airports> getPagedMessages(int page, int size) {
	    // 使用 PageRequest 創建分頁參數，並指定排序
	    Page<Airports> pageResult = airportsRepo.findAll(
	        PageRequest.of(page,  // 查詢的頁數，從0起算
	                       size,  // 查詢的每頁筆數
	                       Sort.by("airportsId").descending())); // 依 airportsId 欄位降冪排序

	    // 直接返回 pageResult
	    return pageResult;
	}
	

}
