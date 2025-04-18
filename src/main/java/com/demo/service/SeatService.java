package com.demo.service;

import java.util.List;

import com.demo.model.Seat;
import com.demo.model.DTO.FlightDTO;
import com.demo.model.DTO.SeatDTO;

public interface SeatService {
	List<SeatDTO> findAllSeat(); // 查詢所有座位
	// 根據航班ID搜尋座位
    List<SeatDTO> findSeatsByFlightId(Integer flightId);
    //鎖定座位
     boolean lockSeat(List<Integer> seatIds, Long userId, int lockMinutes) ;
   //釋放座位方法
    void releaseExpiredLocks() ;

}
