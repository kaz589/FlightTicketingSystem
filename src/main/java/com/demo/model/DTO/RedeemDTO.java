package com.demo.model.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RedeemDTO {
	 private Integer memberId;
	 private List<RedeemItemDTO> redeemItems;

}