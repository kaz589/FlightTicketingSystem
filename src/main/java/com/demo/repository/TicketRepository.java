package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.demo.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

}
