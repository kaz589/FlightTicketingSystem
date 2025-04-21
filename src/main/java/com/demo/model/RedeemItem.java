package com.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity@NoArgsConstructor
@Getter@Setter
@Table(name = "redeem_item")
public class RedeemItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "redeem_item_id")
	private Integer redeemItemId;
	
	@Column(name="quantity")
	private Integer quantity;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "redeem_id", referencedColumnName = "redeem_id")
	@JsonBackReference
	private Redeem redeem;
	
	@Column(name = "used_miles")
	private Integer usedMiles;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	@JsonBackReference
	private Products product;
	
	
	
	
}