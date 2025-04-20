package com.demo.service;

import java.sql.Date;
import java.util.List;


import com.demo.model.Redeem;

//訂單管理
public interface IRedeemService {

	
	/**查詢全部訂單
	 * @return
	 */
	List<Redeem> getAllRedeem();
	
	/**根據Id查單筆
	 * @param redeemId
	 * @return
	 */
	Redeem getRedeemById(Integer redeemId);
	/**
	 * 新增一筆Redeem紀錄
	 * 
	 * @param redeem 新增的Redeem物件
	 * @return 返回新增的Redeem物件
	 */
	Redeem addRedeem(Integer memberId);
	
	
	/**
	 * 更新訂單
	 * @param redeemId
	 * @param Redeem
	 * @return
	 */
	Redeem updateRedeem(Integer redeemId,Redeem Redeem);

	/**
	 * 根據給定的ID刪除對應的Redeem紀錄
	 * 
	 * @param redeem_id 要刪除的Redeem的ID
	 */
	void deleteRedeem(Integer redeemId);

	/**
	 * 根據Redeem狀態查詢對應的所有Redeem紀錄
	 * @param redeem_status Redeem的狀態
	 * @return 返回符合狀態條件的Redeem紀錄列表
	 */
	List<Redeem> findByStatus(String redeemStatus);
	
	List<Redeem> findByTime(Date date);
	
	List<Redeem> findByMemberId(Integer memberId);
	
}