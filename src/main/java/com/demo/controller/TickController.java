package com.demo.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.demo.utils.CheckMacValueCalculator;

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
    public ResponseEntity<Ticket> createTicket( @RequestBody Map<String, Object> body) {
    	String orderId = (String) body.get("orderId");
        Integer customerId = (Integer) body.get("customerId");
        List<Integer> seatIds = (List<Integer>) body.get("seatIds");
    	
        return ticketService.createTicket(orderId,customerId,seatIds);
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

    @GetMapping("/search")
    public Page<TicketDTO> searchTickets(
            @RequestParam(required = false) String originAirport,
            @RequestParam(required = false) String destinationAirport,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date arrivalTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date departureTime,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        return ticketService.searchTickets(
                originAirport,
                destinationAirport,
                arrivalTime,
                departureTime,
                paid,
                keyword,
                page,
                size,
                sortBy,
                sortOrder
        );
    }


    // 根據訂票ID查詢座位
    @GetMapping("/{ticketId}/seats")
    public ResponseEntity<List<Seat>> getSeatsByTicketId(@PathVariable int ticketId) {
        List<Seat> seats = ticketService.findSeatsByTicketId(ticketId);
        return ResponseEntity.ok(seats);
    }
    
    //訂單加密
    @PostMapping("/calculate-mac")
    public String calculateMac(@RequestBody Hashtable <String, String> params) {
        try {
            return CheckMacValueCalculator.genCheckMacValue(params);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error calculating MAC";
        }
    }
    // 查詢指定顧客的所有票券
    @GetMapping("/member/{customerId}")
    public List<TicketDTO> getTicketsByCustomerId(@PathVariable Integer customerId) {
        return ticketService.findTicketsByCustomerId(customerId);
    }
    

}
