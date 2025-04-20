package com.demo.service;

import java.util.List;

import com.demo.model.RedeemItem;

public interface IRedeemItemService {

//	新增訂單項目
	RedeemItem addRedeemItem(Integer redeemId,Integer productId,Integer quantity);
//	刪除訂單項目
	boolean deletedRedeemItem(Integer redeemItemId);
//	修改訂單項目數量
	RedeemItem updateQuantity(Integer redeemItemId,Integer quantity);
//	查看訂單明細
	List<RedeemItem> findRedeemItemByRedeemId(Integer RedeemId);
	
}
