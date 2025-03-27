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
@Table(name="Airports")
public class Airports {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer AirportsId ;
	private String 	IataCode;
	private String 	AirportName;
	private String 	CountryRegion;
	private String 	City;
	private Double	Latitude;
	private Double  Longitude;
	
	
}
