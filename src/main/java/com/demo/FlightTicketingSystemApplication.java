package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlightTicketingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightTicketingSystemApplication.class, args);
	}

}
