package com.demo.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="Flights")
public class Flight {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
    @JoinColumn(name = "origin_iata", referencedColumnName = "iataCode") // 關聯到機場表的 IATA 代碼
    private Airports originAirport; // 起點機場
    
	@ManyToOne
    @JoinColumn(name = "destination_iata", referencedColumnName = "iataCode") // 關聯到機場表的 IATA 代碼
    private Airports destinationAirport; // 終點機場
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於 JSON 請求的日期格式
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於表單數據的日期格式
    private Date departureTime;
   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於 JSON 請求的日期格式
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於表單數據的日期格式
    private Date arrivalTime;
    private int economyPrice;
    private int businessPrice;
    private int firstClassPrice;
    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "airplaneId") // 關聯到飛機型號表的 ID
    private AirplaneModel airplaneModel; // 飛機型號
}
