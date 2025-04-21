package com.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.demo.model.Ticket;
import com.demo.service.MailService;
import com.demo.service.TicketService;


@RestController

public class payController {

	@Autowired	
	private TicketService ticketService;
	@Autowired
	private MailService  mailService;

	@GetMapping("/pay")
	public RedirectView receivePaymentResult(@RequestParam String orderid) {
	    
	    Optional<Ticket> findTicket=ticketService.findTicketByOrderId(orderid);
	    
	    if (findTicket.isPresent()) {
	    	 Ticket ticket = findTicket.get();
	    	// 更新訂單付款狀態
		    ticketService.markTicketPaidByOrderId(orderid);
		    String subject = "訂單 " + orderid + " 付款成功";
	        String content = "訂單 " + orderid + " 付款成功";
		    mailService.sendPlainText("965302@gmail.com", subject, content);
		    // 付款成功後導向結果頁（可依需求更改）
		    return new RedirectView("http://localhost:5173/");
	    }
	    return new RedirectView("http://localhost:5173/");
	}

	
}


