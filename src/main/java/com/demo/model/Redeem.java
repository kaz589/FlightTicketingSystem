package com.demo.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
	@NotBlank(message = "訂單狀態不能為空")
	private String redeemStatus;
	
	@Column(name="create_at")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private	Date createAt;
	
	@Column(name = "is_deleted")
	private boolean deleted;
	
	//訂單總金額
	@Column(name = "totalMiles")
	@Min(value = 0, message = "金額不能小於 0")
	private Integer totalMiles;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
	@JsonBackReference
	private Member member;
}
