package com.demo.model.DTO;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.demo.model.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.demo.model.Flight;
import com.demo.model.Member;
import com.demo.model.Seat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TicketDTO {

	private Integer ticketId;
	private Integer memberId;
	private String memberName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於 JSON 請求的日期格式
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於表單數據的日期格式
	private LocalDateTime bookingTime;
	private int totalAmount;
	private int totalDistance;
	private boolean isPaid;
	private String orderNo;
	private String originAirport;
	private String destinationAirport;
	private int flightid;
	private String departure;
	private String destination;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date  departureDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date  arrivalDate ;
	
	public TicketDTO(Ticket entity) {
		
		
		BeanUtils.copyProperties(entity, this);
		// 用 SeatDTO 計算總價
	    if (entity.getSeats() != null) {
	    	// 票價、距離
	    	this.totalAmount = entity.getSeats().stream()
	    	    .map(seat -> new SeatDTO(seat))
	    	    .mapToInt(seatDTO -> seatDTO.getPrice() != null ? seatDTO.getPrice() : 0)
	    	    .sum();

	    	this.totalDistance = entity.getSeats().stream()
	    	    .map(Seat::getFlight)
	    	    .mapToInt(flight -> flight != null ? flight.getEstimatedDistance() : 0)
	    	    .sum();

	    	// 只取第一個 flight
	    	Flight firstFlight = Optional.ofNullable(entity.getSeats())
	    	    .filter(seats -> !seats.isEmpty())
	    	    .map(seats -> seats.get(0))
	    	    .map(Seat::getFlight)
	    	    .orElse(null);

	    	this.flightid = firstFlight != null ? firstFlight.getId() : 0;
	    	this.departure = firstFlight != null && firstFlight.getOriginAirport() != null
	    	    ? firstFlight.getOriginAirport().getCity()
	    	    : "";
	    	this.destination = firstFlight != null && firstFlight.getDestinationAirport() != null
	    	    ? firstFlight.getDestinationAirport().getCity()
	    	    : "";
	    	this.departureDate = firstFlight != null ? firstFlight.getDepartureTime() : null;
	    	this.arrivalDate   = firstFlight != null ? firstFlight.getArrivalTime()   : null;
	    	this.originAirport = firstFlight != null ? firstFlight.getOriginAirport().getAirportName()   : null;
	    	this.destinationAirport = firstFlight != null ? firstFlight.getDestinationAirport().getAirportName()   : null;
	        
	    } else {
	        this.totalAmount = 0;
	        this.totalDistance=0;
	    }
		this.memberName=entity.getMember().getUsername();
		this.memberId=entity.getMember().getMemberId();
	}
	public Ticket toEntity() {
		Ticket entity = new Ticket();
		BeanUtils.copyProperties(this, entity);
		// 只設置 Member 的 ID
	    Member member = new Member();
	    member.setMemberId(this.memberId); // 從 DTO 中獲取 memberId
	    entity.setMember(member); // 設置到 Ticket 的 Member 屬性
		
		return entity;
	}
}
