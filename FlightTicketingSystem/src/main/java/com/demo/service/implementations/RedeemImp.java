package com.demo.service.implementations;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Redeem;
import com.demo.repository.RedeemRepository;
import com.demo.service.IRedeemService;
@Service
public class RedeemImp implements IRedeemService {

	@Autowired
	private RedeemRepository redeemRepository;
	
	@Override
	public Redeem addRedeem(Redeem redeem) {
	
		return redeemRepository.save(redeem);
	}

	@Override
	public void deleteRedeemById(Integer redeem_id) {
	redeemRepository.deleteById(redeem_id);

	}

	@Override
	public List<Redeem> selectRedeemByStatus(String redeem_status) {
		return redeemRepository.selectRedeemByStatus(redeem_status);
	}

	@Override
	public List<Redeem> selectRedeemByTime(Date redeem_time) {
	
		return redeemRepository.selectRedeemByTime(redeem_time);
	}

}
