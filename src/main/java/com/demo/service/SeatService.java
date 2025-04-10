package com.demo.service;

import java.util.List;

import com.demo.model.Seat;
import com.demo.model.DTO.FlightDTO;
import com.demo.model.DTO.SeatDTO;

public interface SeatService {
	List<SeatDTO> findAllSeat(); // 查詢所有航班
}
