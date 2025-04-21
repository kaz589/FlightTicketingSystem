package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.RedeemItem;
import com.demo.service.IRedeemItemService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/redeemItem")
public class RedeemItemController {

	@Autowired
	private IRedeemItemService redeemItemService;
	
	/**
	 * 刪除訂單項目
	 * @param redeemItemId
	 * @return boolean
	 */
	@DeleteMapping("{redeemItemId}")
	boolean deletedRedeemItem(@PathVariable Integer redeemItemId) {
		return redeemItemService.deletedRedeemItem(redeemItemId);
	};
	
	/**
	 * 修改訂單項目數量
	 * @param redeemItemId
	 * @param quantity
	 * @return redeemItem
	 */
	@PutMapping("{redeemItemId}/updateQuantity")
	RedeemItem updateQuantity(@PathVariable Integer redeemItemId,@RequestParam Integer quantity) {
		return redeemItemService.updateQuantity(redeemItemId, quantity);
	};
	
	/**
	 * 查看訂單明細
	 * @param RedeemId
	 * @return redeemItems
	 */
	@GetMapping("{RedeemId}")
	List<RedeemItem> findRedeemItemByRedeemId(@PathVariable Integer RedeemId){
		return  redeemItemService.findRedeemItemByRedeemId(RedeemId);
	};
}
