package com.demo.model;

import jakarta.persistence.Column;
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
@Table(name="Airports")
public class Airports {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "airports_id") // 數據庫列名
    private Integer airportsId;

    @Column(name = "iata_code") // 數據庫列名
    private String iataCode;

    @Column(name = "airport_name") // 數據庫列名
    private String airportName;

    @Column(name = "country_region") // 數據庫列名
    private String countryRegion;

    @Column(name = "city") // 數據庫列名
    private String city;

    @Column(name = "latitude") // 數據庫列名
    private Double latitude;

    @Column(name = "longitude") // 數據庫列名
    private Double longitude;
	
	
}
