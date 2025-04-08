package com.demo.model.DTO;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.demo.model.AirplaneModel;
import com.demo.model.Airports;
import com.demo.model.Flight;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FlightDTO {

	private Integer id;
	private String originAirport;
	private String destinationAirport;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於 JSON 請求的日期格式
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於表單數據的日期格式
	private Date departureTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於 JSON 請求的日期格式
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於表單數據的日期格式
	private Date arrivalTime;
	private Integer economyPrice;
	private Integer businessPrice;
	private Integer firstClassPrice;
	private String modelName;
	
	public FlightDTO(Flight entity) {
		BeanUtils.copyProperties(entity, this);
		this.setModelName(entity.getAirplaneModel().getModelName());
		this.setOriginAirport(entity.getOriginAirport().getAirportName());
		this.setDestinationAirport(entity.getDestinationAirport().getAirportName());
		
		System.out.println(this.toString());
	}
	
	
	public Flight toEntity(AirplaneModel airplaneModel,Airports originAirport,Airports destinationAirport) {
		Flight entity = new Flight();
		BeanUtils.copyProperties(this, entity);
		entity.setAirplaneModel(airplaneModel);
		entity.setOriginAirport(originAirport);
		entity.setDestinationAirport(destinationAirport);
		
		return entity;
	}
	
	// 默認構造器
    public FlightDTO() {
    }
	
}
