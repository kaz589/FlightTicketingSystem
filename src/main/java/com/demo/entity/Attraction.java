package com.demo.entity;

import java.math.BigDecimal;
import java.util.List;

import com.demo.utils.StringListConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attractions")
@Getter
@Setter
@NoArgsConstructor
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Convert(converter = StringListConverter.class)
    @Column(name = "category")
    private List<String> category;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude", precision = 5, scale = 2)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 5, scale = 2)
    private BigDecimal longitude;

    @Column(name = "rating", precision = 2, scale = 1)
    private BigDecimal rating;

}
