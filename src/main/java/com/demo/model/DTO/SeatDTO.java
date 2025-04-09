package com.demo.model.DTO;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.demo.model.Flight;
import com.demo.model.Seat;
import com.demo.model.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SeatDTO {
	
	private Integer seatId;
	private Flight flight;
	private String seatNumber;
    private String seatClass;
    private Ticket ticket;
    private boolean isBooked;
    private Integer price;
    private String Seatclass;

    public SeatDTO(Seat entity) {
		BeanUtils.copyProperties(entity, this);
		
		
		  // 使用 Spring 提供的 BeanUtils 複製屬性
	    BeanUtils.copyProperties(entity, this);

	    // 計算票價
	    if (entity.getFlight() != null && entity.getSeatClass() != null) {
	        switch (entity.getSeatClass().toLowerCase()) { // 判斷座位艙等
	            case "ECO":
	                this.price = entity.getFlight().getEconomyPrice(); // 經濟艙票價
	                this.Seatclass="經濟艙";
	                break;
	            case "BUS":
	                this.price = entity.getFlight().getBusinessPrice(); // 商務艙票價
	                this.Seatclass="商務艙";
	                break;
	            case "FIR":
	                this.price = entity.getFlight().getFirstClassPrice(); // 頭等艙票價
	                this.Seatclass="頭等艙";
	                break;
	            default:
	                this.price = 0; // 如果艙等無效，設置為 0 或其他默認值
	                break;
	        }
	    } else {
	        this.price = 0; // 如果航班或艙等信息不存在，設置為 0
	    }
		
		
	}
}
