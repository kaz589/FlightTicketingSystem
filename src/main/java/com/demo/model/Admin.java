package com.demo.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "admin")
public class Admin {
	
	@Id @Column(name = "admin_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer adminId;
	
	@Column(name = "full_name")
	String fullName;
	
	@Column(name = "username")
	String username;
	
	@Column(name = "password")
	String password;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "phone_number")
	String phoneNumber;
	
	@Column(name = "registration_date")
	Date registrationDate;
	
	
	
	
	public Admin() {
		super();
	}




	public Admin(String fullName, String username, String password, String email, String phoneNumber,
			Date registrationDate) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.registrationDate = registrationDate;
	}
	

	





	
}
