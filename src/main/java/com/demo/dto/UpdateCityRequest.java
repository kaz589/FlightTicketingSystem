package com.demo.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCityRequest {
    private String name;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String image;
}
