package com.demo.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Seat;
import com.demo.repository.SeatRepository;
import com.demo.service.SeatService;
@Service
public class Seatimp  implements SeatService{

	@Autowired
	private SeatRepository seatRepository;
	
	@Override
	public List<Seat> findAllSeat() {
		return seatRepository.findAll();
	}

}
