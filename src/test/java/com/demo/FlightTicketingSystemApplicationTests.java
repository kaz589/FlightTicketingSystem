package com.demo;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.service.MailService;

@SpringBootTest
class FlightTicketingSystemApplicationTests {

	@Autowired	
    private	MailService mailService;
	
	
	@Test
	void contextLoads() {
		
		
		mailService.sendPlainText("965302@gmail.com", "123", "123");
	}

}
