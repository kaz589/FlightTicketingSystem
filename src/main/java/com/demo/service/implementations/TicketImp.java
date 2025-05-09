package com.demo.service.implementations;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.aspectj.weaver.NewMemberClassTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.demo.model.Member;
import com.demo.model.Seat;
import com.demo.model.Ticket;
import com.demo.model.DTO.FlightDTO;
import com.demo.model.DTO.SeatDTO;
import com.demo.model.DTO.TicketDTO;
import com.demo.repository.FlightRepository;
import com.demo.repository.SeatRepository;
import com.demo.repository.TicketRepository;
import com.demo.repository.MemberRepository;
import com.demo.service.SeatService;
import com.demo.service.TicketService;

import jakarta.transaction.Transactional;

@Service
public class TicketImp implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private SeatService seatService;

	@Override
	public List<TicketDTO> findAllTickets() {

		List<TicketDTO> newTicketDTO = ticketRepository.findAll().stream()
				.map(TicketDTO::new)
				.toList();
		System.out.println(newTicketDTO.toString());
		// TODO Auto-generated method stub
		return newTicketDTO;
	}

	@Override
	public Optional<TicketDTO> findTicketById(int id) {
		Optional<TicketDTO> TicketDTO= ticketRepository.findById(id)
			    .map(TicketDTO::new);
		return TicketDTO;
	}

	@Override
	public Ticket updateTicket(int id, TicketDTO ticketDetails) {

		Ticket ticket = ticketRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "訂單未找到"));

		Ticket newtTicket = ticketDetails.toEntity();

		return ticketRepository.save(newtTicket);
	}

	@Override
	public void deleteTicket(int id) {

		
		seatService.findSeatsByTicketId(id);
		
		List<Integer> seatIds = seatService.findSeatsByTicketId(id).stream()
			    .map(SeatDTO::getSeatId)
			    .collect(Collectors.toList());
		seatService.releaseSeatsByIds(seatIds);
		ticketRepository.deleteById(id);
		
	}

	

	

	@Override
	@Transactional
	public ResponseEntity<Ticket> createTicket(String orderId, Integer customerId, List<Integer> seatIds) {
		Ticket newTicket = new Ticket();

		Member member = new Member();
		LocalDateTime now = LocalDateTime.now();
		member.setMemberId(customerId);
		newTicket.setMember(member);
		newTicket.setOrderNo(orderId);
		newTicket.setBookingTime(now);

		newTicket = ticketRepository.save(newTicket);

		seatService.lockSeat(seatIds, newTicket, 10);
		return ResponseEntity.ok(newTicket);
	}

	@Override
	public Ticket markTicketPaidByOrderId(String orderId) {
		Ticket ticket = ticketRepository.findByOrderNo(orderId)
				.orElseThrow(() -> new RuntimeException("Ticket not found"));
		ticket.setPaid(true);
		return ticketRepository.save(ticket);
	}

	@Override
	public Optional<Ticket> findTicketByOrderId(String orderId) {
		return ticketRepository.findByOrderNo(orderId);
	}

	@Override
	public List<TicketDTO> findTicketsByCustomerId(Integer customerId) {

		List<TicketDTO> newTicketDTO = ticketRepository.findByMemberId(customerId).stream()
				.map(TicketDTO::new)
				.toList();

		return newTicketDTO;
	}

	@Override
	public Page<TicketDTO> searchTickets(String originAirport, String destinationAirport, Date startTime, Date endTime,
			Boolean paymentStatus, String keyword, int page, int size, String sortBy, String sortOrder) {
		
		
		// 處理排序邏輯
        Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		    
		PageRequest  pageable = PageRequest.of(page-1, size,sort);
		
		Page<Ticket> ticketPage =ticketRepository.searchTicket(originAirport, destinationAirport, startTime, paymentStatus, endTime, keyword, pageable);
		Page<TicketDTO> dtoPage = ticketPage.map(TicketDTO::new);
		return dtoPage;
	}

	@Override
	public List<Seat> findSeatsByTicketId(int ticketId) {
		// TODO Auto-generated method stub
		return null;
	}

}
