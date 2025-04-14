package com.demo.model;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="Tickets")
public class Ticket {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	   
	    private Integer ticketId;

	    @ManyToOne
	    @JoinColumn(name = "member_id", referencedColumnName = "member_id") // 關聯到顧客表
	    private Member member;

	   
	    private LocalDateTime bookingTime;
	   
	    // 一對多關係：一張票對應多個座位
	    @OneToMany(mappedBy = "ticket")
	    private List<Seat> seats;
}
