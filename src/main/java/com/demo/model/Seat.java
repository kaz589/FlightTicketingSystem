package com.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="Seats")
public class Seat {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seatId;
    private int flightId;
    private Integer memberId; // 可為 null
    private String seatNumber;
    private String seatClass;
    private Integer ticketId; // 可為 null
    private boolean isBooked;
    
 // 構造函數
    public Seat(int flightId, String seatNumber, String seatClass, boolean isBooked) {
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.isBooked = isBooked;
    }
}
