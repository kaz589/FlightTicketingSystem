package com.demo.service;

import java.util.List;

import com.demo.model.Seat;
import com.demo.model.DTO.FlightDTO;

public interface SeatService {
	List<Seat> findAllSeat(); // 查詢所有航班
}
