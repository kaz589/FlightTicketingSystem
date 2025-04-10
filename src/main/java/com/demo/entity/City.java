package com.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cities")
@Getter
@Setter
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "country")
    private String country;
    @Column(name = "latitude", precision = 5, scale = 2)
    private BigDecimal latitude;
    @Column(name = "longitude", precision = 5, scale = 2)
    private BigDecimal longitude;
    @Column(name = "image")
    private String image;

    public City(Long id) {
        this.id = id;
    }
}
