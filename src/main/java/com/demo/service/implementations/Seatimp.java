package com.demo.service.implementations;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Seat;
import com.demo.model.Ticket;
import com.demo.model.DTO.FlightDTO;
import com.demo.model.DTO.SeatDTO;
import com.demo.repository.SeatRepository;
import com.demo.service.SeatService;

import jakarta.transaction.Transactional;
@Service
public class Seatimp  implements SeatService{

	@Autowired
	private SeatRepository seatRepository;
	
	@Override
	public List<SeatDTO> findAllSeat() {
		
		 List <SeatDTO> newSeatDTO=seatRepository.findAll().stream()
					.map(SeatDTO::new)
					.toList();
		 
		 
		
		
		return newSeatDTO;
	}

	@Override
	public List<SeatDTO> findSeatsByFlightId(Integer flightId) {
		 List <SeatDTO> newSeatDTO=seatRepository.findSeatsByFlightId(flightId).stream()
					.map(SeatDTO::new)
					.toList();
		 
		 
		
		
		return newSeatDTO;
	}

	
	@Override
	@Transactional
	public void  lockSeat(List<Integer> seatIds,  Ticket newTicket, int lockMinutes) {
		// 1. 查詢所有 seatId 是否有有效鎖定（一次查詢避免N+1）
	    List<Seat> existingLocks = seatRepository.findValidLocks(seatIds);
	    if (!existingLocks.isEmpty()) {
	    	 throw new IllegalStateException("座位鎖定失敗");
	    }

	    // 2. 全部可鎖定，批量建立 SeatLock
	    List<Seat> seats = seatRepository.findAllById(seatIds);

	    LocalDateTime now = LocalDateTime.now();
	    LocalDateTime expires = now.plusMinutes(lockMinutes);
	    for (Seat seat : seats) {
	        seat.setLocked_at(now);
	        seat.setExpires_at(expires);
	        seat.setBooked(true); // 鎖定時尚未訂票
	        seat.setTicket(newTicket);
	        // 可以考慮記錄 userId（如果 Seat 有此欄位）
	    }

	    seatRepository.saveAll(seats);
	    
	}
	@Override
	@Transactional
	public void releaseExpiredLocks() {
		 LocalDateTime now = LocalDateTime.now();
		    // 查詢過期、未訂票且未關聯 ticket 的座位
		    List<Seat> expiredSeats = seatRepository.findExpiredAndUnbookedAndTicketIsNull(now);
		    if (expiredSeats.isEmpty()) {
		    	System.out.println("查無未付款座位");
		        return;
		    }
		    for (Seat seat : expiredSeats) {
		        seat.setLocked_at(null);
		        seat.setExpires_at(null);
		        seat.setBooked(false);
		        seat.setTicket(null);
		    }
		    seatRepository.saveAll(expiredSeats);
		
	}

	@Override
	public List<SeatDTO> findSeatsByTicketId(Integer ticketId) {
		List <SeatDTO> newSeatDTO=seatRepository.findSeatsByTicketId(ticketId).stream()
				.map(SeatDTO::new)
				.toList();
	 
	 
	System.out.println(newSeatDTO);
	
	return newSeatDTO;
	}

	@Override
	public void releaseSeatById(Integer seatId) {
		 Seat seat = seatRepository.findById(seatId)
			        .orElseThrow(() -> new IllegalArgumentException("找不到該座位: " + seatId));
			    seat.setLocked_at(null);
			    seat.setExpires_at(null);
			    seat.setBooked(false);
			    seat.setTicket(null);
			    seatRepository.save(seat);
		
	}

	@Override
	public void releaseSeatsByIds(List<Integer> seatIds) {
		List<Seat> seats = seatRepository.findAllById(seatIds);
	    for (Seat seat : seats) {
	        seat.setLocked_at(null);
	        seat.setExpires_at(null);
	        seat.setBooked(false);
	        seat.setTicket(null);
	    }
	    seatRepository.saveAll(seats);
		
	}

	@Override
	public List<SeatDTO> findSeatsByFlightIdAndBooked(Integer flightId, boolean booked) {
		
		
		 List <SeatDTO> newSeatDTO=seatRepository.findByFlightIdAndBooked( flightId,  booked).stream()
		.map(SeatDTO::new)
		.toList();
		
		return newSeatDTO;
	}

	

	

}
