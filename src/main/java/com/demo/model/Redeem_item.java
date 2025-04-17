package com.demo.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Component
public class Redeem_item {

	@Id@Column(name = "redeem_item_id")
	private Integer redeemItemId;
	
	@Column(name="quantity")
	private Integer quantity;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "redeem_id", referencedColumnName = "redeem_id")
	@JsonBackReference
	private Redeem redeem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	@JsonBackReference
	private Products products;
	
	
}