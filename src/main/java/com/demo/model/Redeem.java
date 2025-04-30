package com.demo.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter@Setter
@Entity@Table(name = "Redeem")
public class Redeem {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "redeem_id")
	private Integer redeemId;
		
	@Column(name="redeem_status")
	@NotBlank(message = "訂單狀態不能為空")
	private String redeemStatus;
	
	@Column(name="create_at", insertable = false, updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 用於 JSON 請求的日期格式
	private	LocalDateTime createAt;
	
	 
	
	@Column(name = "is_deleted")
	private boolean deleted;
	
	//訂單總金額
	@Column(name = "redeem_total_miles")
	@Min(value = 0, message = "金額不能小於 0")
	private Integer redeemTotalMiles;
	
	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
	private Member member;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "redeem")
	@JsonManagedReference
	private List<RedeemItem> redeemItems;
}
