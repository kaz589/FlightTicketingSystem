package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

	// 使用 JPQL 語法根據 Flight 中的 flightId 搜尋座位
    @Query("SELECT s FROM Seat s WHERE s.flight.id = :flightId")
    List<Seat> findSeatsByFlightId(@Param("flightId") Integer flightId);
}
