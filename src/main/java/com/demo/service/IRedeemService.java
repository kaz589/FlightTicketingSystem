package com.demo.service;

import java.util.Date;
import java.util.List;

import com.demo.model.Redeem;

public interface IRedeemService {

	/**
	 * 新增一筆Redeem紀錄
	 * 
	 * @param redeem 新增的Redeem物件
	 * @return 返回新增的Redeem物件
	 */
	Redeem addRedeem(Redeem redeem);

	/**
	 * 根據給定的ID刪除對應的Redeem紀錄
	 * 
	 * @param redeem_id 要刪除的Redeem的ID
	 */
	void deleteRedeemById(Integer redeemId);

	/**
	 * 根據Redeem狀態查詢對應的所有Redeem紀錄
	 * 
	 * @param redeem_status Redeem的狀態
	 * @return 返回符合狀態條件的Redeem紀錄列表
	 */
	List<Redeem> selectRedeemByStatus(String redeemStatus);

	/**
	 * 根據Redeem時間查詢對應的所有Redeem紀錄
	 * 
	 * @param redeem_time Redeem的時間
	 * @return 返回符合時間條件的Redeem紀錄列表
	 */
	List<Redeem> selectRedeemByTime(Date redeemTime);
}
