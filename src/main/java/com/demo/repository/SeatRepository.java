package com.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

	// 使用 JPQL 語法根據 Flight 中的 flightId 搜尋座位
    @Query("SELECT s FROM Seat s WHERE s.flight.id = :flightId")
    List<Seat> findSeatsByFlightId(@Param("flightId") Integer flightId);
    
 // 使用 JPQL 語法根據 Flight 中的 flightId 搜尋座位
    @Query("SELECT s FROM Seat s WHERE s.ticket.ticketId= :ticketId")
    List<Seat> findSeatsByTicketId(@Param("ticketId") Integer ticketId);
    
    //查詢座位是否未上鎖
    @Query("SELECT s FROM Seat s WHERE s.id IN :seatIds AND s.ticket IS NOT NULL AND s.booked!=null")
    List<Seat> findValidLocks(@Param("seatIds") List<Integer> seatIds);
   
    //查詢過期且未訂票的座位
    @Query("""
    		SELECT s FROM Seat s
    		LEFT JOIN s.ticket t
    		WHERE s.expires_at < :now
    		  AND s.booked = false
    		  AND (s.ticket IS NULL OR t.isPaid = false)
    		""")
    List<Seat> findExpiredAndUnbookedAndTicketIsNull(@Param("now") LocalDateTime now);
}
