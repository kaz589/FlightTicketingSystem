package com.demo.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Seat;
import com.demo.model.DTO.FlightDTO;
import com.demo.model.DTO.SeatDTO;
import com.demo.repository.SeatRepository;
import com.demo.service.SeatService;
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
	public boolean lockSeat(List<Integer> seatIds, Long userId, int lockMinutes) {
//		// 1. 查詢所有 seatId 是否有有效鎖定（一次查詢避免N+1）
//	    List<SeatLock> existingLocks = seatRepository.findValidLocks(seatIds, LocalDateTime.now());
//	    if (!existingLocks.isEmpty()) {
//	        return false; // 有任何一個座位已被鎖定
//	    }
//
//	    // 2. 全部可鎖定，批量建立 SeatLock
//	    LocalDateTime now = LocalDateTime.now();
//	    LocalDateTime expires = now.plusMinutes(lockMinutes);
//
//	    List<SeatLock> locks = seatIds.stream().map(seatId -> {
//	        SeatLock lock = new SeatLock();
//	        lock.setSeatId(seatId);
//	        lock.setUserId(userId);
//	        lock.setLockedAt(now);
//	        lock.setExpiresAt(expires);
//	        lock.setStatus("LOCKED");
//	        return lock;
//	    }).collect(Collectors.toList());
//
//	    seatLockRepository.saveAll(locks);
	    return true;
	}

	@Override
	public void releaseExpiredLocks() {
		// TODO Auto-generated method stub
		
	}

}
