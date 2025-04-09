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
	 * 根據Redeem狀態查詢符合條件的Redeem紀錄
	 * 
	 * @param redeem_status Redeem的狀態
	 * @return 返回符合條件的Redeem紀錄列表
	 */
	@Query("FROM Redeem r WHERE redeemStatus=:status")
	List<Redeem> selectRedeemByStatus(@Param("status") String redeemStatus);

	/**
	 * 根據Redeem時間查詢符合條件的Redeem紀錄
	 * 
	 * @param redeem_time Redeem的時間
	 * @return 返回符合條件的Redeem紀錄列表
	 */
	@Query("FROM Redeem r WHERE redeemTime=:redeemTime")
	List<Redeem> selectRedeemByTime(@Param("redeemTime") Date redeemTime);

}
