package com.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.model.Redeem;

@Repository
public interface RedeemRepository extends JpaRepository<Redeem, Integer> {

	/**
	 * 根據Redeem時間查詢符合條件的Redeem紀錄
	 * 
	 * @param redeem_time Redeem的時間
	 * @return 返回符合條件的Redeem紀錄列表
	 */
	List<Redeem> findByCreateAt(Date date);
	
	List<Redeem> findByMember_MemberId(Integer memberId);

	List<Redeem> findByRedeemStatus(String redeemStatus);


	

}
