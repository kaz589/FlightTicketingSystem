package com.demo.model;

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
@Table(name = "airplane_models")
public class AirplaneModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動增長主鍵
    private Integer airplaneId; // 飛機ID
    private String modelName; // 飛機型號名稱，最多10個字符
    private Integer seatCount; // 總座位數量
    private Integer seatRowCount; // 座位排數
    private Integer aisleStartPosition; // 走道起始位置（以排數表示）
    private Integer economySeatStart; // 經濟艙起始座位
    private Integer economySeatEnd; // 經濟艙結束座位
    private Integer businessSeatStart; // 商務艙起始座位
    private Integer businessSeatEnd; // 商務艙結束座位
    private Integer firstClassSeatStart; // 頭等艙起始座位
    private Integer firstClassSeatEnd; // 頭等艙結束座位
}
