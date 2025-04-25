package com.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.demo.model.Ticket;
import com.demo.model.DTO.TicketDTO;
import com.demo.service.MailService;
import com.demo.service.MemberService;
import com.demo.service.TicketService;

import jakarta.mail.MessagingException;


@RestController

public class payController {

	@Autowired	
	private TicketService ticketService;
	@Autowired
	private MailService  mailService;
	@Autowired
	private MemberService memberService;

	@GetMapping("/pay")
	public RedirectView receivePaymentResult(@RequestParam String orderid) {
	    
	    Optional<Ticket> findTicket=ticketService.findTicketByOrderId(orderid);
	    
	    if (findTicket.isPresent()) {
	    	
	    	 Ticket ticket = findTicket.get();
	    	 TicketDTO newTicketDTO=new TicketDTO(ticket);
	    	 memberService.increaseMilesById(ticket.getMember().getMemberId(), newTicketDTO.getTotalDistance());
	    	// 更新訂單付款狀態
		    ticketService.markTicketPaidByOrderId(orderid);
		    try {
				mailService.sendOrderPaidMail(ticket.getMember().getEmail(),ticket.getMember().getFullName() ,ticket.getOrderNo(),"NT$"+newTicketDTO.getTotalAmount());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    // 付款成功後導向結果頁（可依需求更改）
		    return new RedirectView("http://localhost:5173/");
	    }
	    return new RedirectView("http://localhost:5173/");
	}

	
}


