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
    private boolean booked;
    private Integer price;
    private String Seatclasname;

    public SeatDTO(Seat entity) {
		BeanUtils.copyProperties(entity, this);
		

	    // 計算票價
	    if (entity.getFlight() != null && entity.getSeatClass() != null) {
	    	System.out.println(entity.getSeatClass());
	        switch (entity.getSeatClass()) { // 判斷座位艙等
	            case "ECO":
	            	
	                this.price = entity.getFlight().getEconomyPrice(); // 經濟艙票價
	                System.out.println(this.price);
	                this.Seatclasname="經濟艙";
	                break;
	            case "BUS":
	                this.price = entity.getFlight().getBusinessPrice(); // 商務艙票價
	                this.Seatclasname="商務艙";
	                break;
	            case "FIR":
	                this.price = entity.getFlight().getFirstClassPrice(); // 頭等艙票價
	                this.Seatclasname="頭等艙";
	                break;
	            default:
	                this.price = 0; // 如果艙等無效，設置為 0 或其他默認值
	                break;
	        }
	    } else {
	        this.price = 0; // 如果航班或艙等信息不存在，設置為 0
	    }
		System.out.println();
		
	}
}
