package com.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
	
	Optional<Ticket> findByOrderNo(String orderNo);
	
	@Query("SELECT t FROM Ticket t WHERE t.member.memberId = :memberId")
	List<Ticket> findByMemberId(@Param("memberId") Integer memberId);

}
