package com.demo.model;

import java.util.Date;

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

@NoArgsConstructor
@Getter@Setter
@Entity@Table(name = "redeem")
public class Redeem {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "redeem_id")
	private Integer redeemId;
		
	@Column(name="redeem_status")
	private String redeemStatus;
	
	@Column(name="redeem_time")
	private	Date redeemTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
	@JsonBackReference
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	@JsonBackReference
	private Products product;
}
