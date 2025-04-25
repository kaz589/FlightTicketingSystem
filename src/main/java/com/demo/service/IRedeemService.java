package com.demo.service;

import java.sql.Date;
import java.util.List;


import com.demo.model.Redeem;
import com.demo.model.RedeemItem;
import com.demo.model.DTO.RedeemDTO;

//訂單管理
public interface IRedeemService {

	
	/**查詢全部訂單
	 * @return redeems
	 */
	List<Redeem> getAllRedeem();
	
	/**根據Id查單筆
	 * @param redeemId
	 * @return redeem
	 */
	Redeem getRedeemById(Integer redeemId);
	
	/**
	 * 新增一筆訂單
	 * @param createRedeemRequest
	 * @return redeem 
	 */
	Redeem addRedeem(RedeemDTO createRedeemRequest);
	
	
	/**
	 * 更新訂單
	 * @param redeemId
	 * @param Redeem
	 * @return redeem
	 */
	Redeem updateRedeem(Integer redeemId,Redeem Redeem);
	
	
	
	/** 更新訂單狀態
	 * @param redeemId
	 * @param newStatus
	 * @return redeem
	 */
	public Redeem updateRedeemStatus(Integer redeemId, String newStatus);

	/**
	 * 根據給定的ID刪除對應的Redeem紀錄
	 * @param redeemId 
	 * @return redeem 帶有軟刪除的屬性
	 */
	Redeem softDeleteRedeem(Integer redeemId);

	/**
	 * 根據Redeem狀態查詢對應的所有Redeem紀錄
	 * @param redeem_status Redeem的狀態
	 * @return 返回符合狀態條件的Redeem紀錄列表
	 */
	List<Redeem> findByRedeemStatus(String redeemStatus);
//	根據訂單成立時間查詢訂單紀錄
	List<Redeem> findByTime(Date date);
//	根據會員ID查詢會員訂單
	List<Redeem> findByMemberId(Integer memberId);
	
	/**
	 * 取消訂單
	 * @param redeemId
	 * @return Redeem
	 */
	Redeem cancelRedeem(Integer redeemId);
	
}