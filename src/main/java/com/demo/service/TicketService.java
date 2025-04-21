package com.demo.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.model.Member;
import com.demo.model.Seat;
import com.demo.model.Ticket;
import com.demo.model.DTO.TicketDTO;

public interface TicketService {

	
	 // 查詢所有票
    List<TicketDTO> findAllTickets();
    
    // 根據 ID 查詢票
    Optional<TicketDTO> findTicketById(int id);
    // 根據顧客ID查詢票
    List<TicketDTO> findTicketsByCustomerId(Integer customerId);
    
    // 新增票
    ResponseEntity<Ticket> createTicket(String orderId, Integer customerId, List<Integer> seatIds);
    
    // 更新票
    Ticket updateTicket(int id, TicketDTO ticketDetails);
    
    // 刪除票
    void deleteTicket(int id);
    
    // 搜索票
    Page<Ticket> searchTickets(Member member, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime,
                               int flightId, Pageable pageable);
    
    // 查詢票的座位
    List<Seat> findSeatsByTicketId(int ticketId);
    // 根據 orderId 設定訂單為已付款
    Ticket markTicketPaidByOrderId(String orderId);
    
    Optional<Ticket> findTicketByOrderId(String orderId);

	
}
