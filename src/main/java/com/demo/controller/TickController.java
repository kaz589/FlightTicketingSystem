package com.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Member;
import com.demo.model.Seat;
import com.demo.model.Ticket;
import com.demo.model.DTO.TicketDTO;
import com.demo.service.TicketService;

@RestController
@RequestMapping("/api/Ticket")
public class TickController {
	
	@Autowired
	private TicketService ticketService;
	
	 // 查詢所有訂票
    @GetMapping("/getAll")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> tickets = ticketService.findAllTickets();
        return ResponseEntity.ok(tickets);
    }

    // 根據ID查詢訂票
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable int id) {
        Optional<TicketDTO> ticket = ticketService.findTicketById(id);
        return ticket.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 創建新訂票
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO) {
        return ticketService.createTicket(ticketDTO);
    }

    // 更新訂票
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int id, @RequestBody TicketDTO ticketDetails) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticketDetails);
        return ResponseEntity.ok(updatedTicket);
    }

    // 刪除訂票
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable int id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 搜尋訂票
    @GetMapping("/search")
    public ResponseEntity<Page<Ticket>> searchTickets(
            @RequestParam(required = false) Integer memberId,
            @RequestParam(required = false) LocalDateTime bookingStartTime,
            @RequestParam(required = false) LocalDateTime bookingEndTime,
            @RequestParam(required = false) Integer flightId,
            Pageable pageable) {

        Member member = null;
        if (memberId != null) {
            member = new Member();
            member.setMemberId(memberId);
        }

        Page<Ticket> tickets = ticketService.searchTickets(member, bookingStartTime, bookingEndTime, flightId, pageable);
        return ResponseEntity.ok(tickets);
    }

    // 根據訂票ID查詢座位
    @GetMapping("/{ticketId}/seats")
    public ResponseEntity<List<Seat>> getSeatsByTicketId(@PathVariable int ticketId) {
        List<Seat> seats = ticketService.findSeatsByTicketId(ticketId);
        return ResponseEntity.ok(seats);
    }

}
