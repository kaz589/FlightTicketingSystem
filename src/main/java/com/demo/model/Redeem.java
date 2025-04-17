package com.demo.model;

import java.util.Date;

import org.springframework.stereotype.Component;

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
	
	@Column(name="create_at")
	private	Date createAt;
	
	@Column(name = "is_deleted")
	private boolean isDeleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
	@JsonBackReference
	private Member member;
}
