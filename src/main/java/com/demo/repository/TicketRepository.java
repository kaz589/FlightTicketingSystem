package com.demo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
	
	Optional<Ticket> findByOrderNo(String orderNo);
	
	@Query("SELECT t FROM Ticket t WHERE t.member.memberId = :memberId")
	List<Ticket> findByMemberId(@Param("memberId") Integer memberId);
	
	@Query("SELECT t FROM Ticket t " +
		       "JOIN t.seats s " +
		       "JOIN s.flight f " +
		       "WHERE " +
		       "(:originAirport IS NULL OR f.originAirport.airportName = :originAirport) AND " +
		       "(:destinationAirport IS NULL OR f.destinationAirport.airportName = :destinationAirport) AND " +
		       "(:startTime IS NULL OR f.departureTime >= :startTime) AND " +
		       "(:endTime IS NULL OR f.arrivalTime <= :endTime) AND " +
		       "(:paymentStatus IS NULL OR t.isPaid = :paymentStatus) AND " +
		       "(:keyword IS NULL OR " +
		       "  t.ticketId =:keyword OR " +
		       "  f.id =:keyword OR " +
		       "  t.member.username LIKE %:keyword% )"
		)
	Page<Ticket> searchTicket(
		    @Param("originAirport") String originAirport,
		    @Param("destinationAirport") String destinationAirport,
		    @Param("startTime") Date startTime,
		    @Param("paymentStatus") Boolean paymentStatus, // 新增的付款狀態參數
		    @Param("endTime") Date endTime,
		    @Param("keyword") String keyword,
		    Pageable pageable
		);

}
