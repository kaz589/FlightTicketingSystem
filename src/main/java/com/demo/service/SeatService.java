package com.demo.service;

import java.util.List;

import com.demo.model.Seat;
import com.demo.model.Ticket;
import com.demo.model.DTO.FlightDTO;
import com.demo.model.DTO.SeatDTO;

public interface SeatService {
	List<SeatDTO> findAllSeat(); // 查詢所有座位
	// 根據航班ID搜尋座位
    List<SeatDTO> findSeatsByFlightId(Integer flightId);
    
    List<SeatDTO>findSeatsByTicketId(Integer ticketId);
    //鎖定座位
    void lockSeat(List<Integer> seatIds, Ticket newTicket, int lockMinutes);
   //釋放座位方法
    void releaseExpiredLocks() ;
	

}
