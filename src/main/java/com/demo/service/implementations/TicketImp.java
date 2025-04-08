package com.demo.service.implementations;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.demo.model.Member;
import com.demo.model.Seat;
import com.demo.model.Ticket;
import com.demo.model.DTO.FlightDTO;
import com.demo.model.DTO.TicketDTO;
import com.demo.repository.FlightRepository;
import com.demo.repository.SeatRepository;
import com.demo.repository.TicketRepository;
import com.demo.repository.MemberRepository;
import com.demo.service.TicketService;
@Service
public class TicketImp implements TicketService {
	
	
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private SeatRepository  seatRepository;
	@Autowired
	private MemberRepository memberRepository;
	
	
	
	@Override
	public List<TicketDTO> findAllTickets() {
		
		List<TicketDTO> newTicketDTO=ticketRepository.findAll().stream()
				.map(TicketDTO::new)
				.toList();
		// TODO Auto-generated method stub
		return newTicketDTO;
	}

	@Override
	public Optional<TicketDTO> findTicketById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public ResponseEntity<Ticket> createTicket(TicketDTO ticketDTO) {
		
		Ticket newtTicket=ticketDTO.toEntity();
		
		return ResponseEntity.ok(ticketRepository.save(newtTicket));
	}

	@Override
	public Ticket updateTicket(int id, TicketDTO ticketDetails) {
		
		Ticket ticket =ticketRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "訂單未找到"));
		
		Ticket newtTicket=ticketDetails.toEntity();
		
		return ticketRepository.save(newtTicket);
	}

	@Override
	public void deleteTicket(int id) {
		
		ticketRepository.deleteById(id);
	}

	@Override
	public Page<Ticket> searchTickets(Member member, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime,
			int flightId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seat> findSeatsByTicketId(int ticketId) {
		// TODO Auto-generated method stub
		return null;
	}

}
