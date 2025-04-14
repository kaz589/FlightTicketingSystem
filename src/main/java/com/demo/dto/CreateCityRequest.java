package com.demo.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCityRequest {
    private Long id;
    private String name;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String image;
}
