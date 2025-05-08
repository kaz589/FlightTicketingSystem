package com.demo.model.DTO;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.demo.model.Flight;
import com.demo.model.Redeem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter@Setter
public class RedeemDTO {
	 private Integer memberId;
	 private List<RedeemItemDTO> redeemItems;

	//EntitytoDTO
		public RedeemDTO(Redeem entity) {
			BeanUtils.copyProperties(entity, this);
			
			
			
		}

}