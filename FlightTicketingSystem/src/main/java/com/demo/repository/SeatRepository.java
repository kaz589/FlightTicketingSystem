package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

}
