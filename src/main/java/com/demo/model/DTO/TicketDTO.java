package com.demo.model.DTO;


import java.time.LocalDateTime;




import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.demo.model.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
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
	
	public TicketDTO(Ticket entity) {
		
		
		BeanUtils.copyProperties(entity, this);
		// 用 SeatDTO 計算總價
	    if (entity.getSeats() != null) {
	        this.totalAmount = entity.getSeats().stream()
	            .map(seat -> new SeatDTO(seat)) // 轉 SeatDTO，計算票價
	            .mapToInt(seatDTO -> seatDTO.getPrice() != null ? seatDTO.getPrice() : 0)
	            .sum();
	        this.totalDistance = entity.getSeats().stream()
	        	    .map(Seat::getFlight) // 取得每個座位的航班
	        	    .mapToInt(flight -> flight != null ? flight.getEstimatedDistance() : 0)
	        	    .sum();
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
