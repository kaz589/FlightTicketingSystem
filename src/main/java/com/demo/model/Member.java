package com.demo.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {
	
	@Id@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer memberId;
	
	@Column(name = "full_name")
	String fullName;
	
	@Column(name = "username")
	String username;
	
	@Column(name = "password")
	String password;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "total_miles")
	Integer totalMiles;
	
	@Column(name = "remaining_miles")
	Integer remainingMiles;
	
	@Column(name = "phone_number")
	String phoneNumber;
	
	@Column(name = "registration_date")
	Date registrationDate;
	
	@Column(name = "email_verified")
	boolean emailVerified;
	
	@Column(name = "phone_verified")
	boolean phoneVerified;
	
	@Column(name = "membership_level")
	String membershipLevel;

	public Member() {
		super();
	}

	public Member(String fullName, String username, String password, String email, Integer totalMiles,
			Integer remainingMiles, String phoneNumber, Date registrationDate, boolean emailVerified,
			boolean phoneVerified, String membershipLevel) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.totalMiles = totalMiles;
		this.remainingMiles = remainingMiles;
		this.phoneNumber = phoneNumber;
		this.registrationDate = registrationDate;
		this.emailVerified = emailVerified;
		this.phoneVerified = phoneVerified;
		this.membershipLevel = membershipLevel;
	}

	
	
	

	
	
	
	

}
