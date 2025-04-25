package com.demo.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Redeem;
import com.demo.model.DTO.RedeemDTO;
import com.demo.service.IRedeemService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/redeem")
public class RedeemController {

	@Autowired
	private IRedeemService redeemService;
	

	/**查詢全部訂單
	 * @return 所有訂單的列表
	 */
	@GetMapping("/")
	public List<Redeem> getAllRedeem(){
		return 	redeemService.getAllRedeem();
	}
	/**根據Id查單筆
	 * @param redeemId
	 * @return
	 */
	@GetMapping("/{redeemId}")
	public Redeem getRedeemById(@PathVariable Integer redeemId) {
		return redeemService.getRedeemById(redeemId);
	};
	
	/**
	 * 新增一筆訂單
	 * @param createRedeemRequest
	 * @return
	 */
	@PostMapping
	public Redeem addRedeem(@RequestBody @Valid RedeemDTO createRedeemRequest) {
		return redeemService.addRedeem(createRedeemRequest);
		
	};
	
	
	/**
	 * 更新訂單
	 * @param redeemId
	 * @param Redeem
	 * @return redeem
	 */
	@PutMapping("/{redeemId}")
	public Redeem updateRedeem(@PathVariable Integer redeemId,@RequestBody @Valid Redeem Redeem) {
		return redeemService.updateRedeem(redeemId, Redeem);
	}
	
	/**
	 * @param redeemId
	 * @param newStatus
	 * @return redeem
	 */
	@PutMapping("/{redeemId}/status")
	public Redeem updateRedeemStatus(@PathVariable Integer redeemId,@RequestParam String newStatus) {
		return redeemService.updateRedeemStatus(redeemId, newStatus);
	}
	/**
	 * 根據redeemId刪除對應的Redeem紀錄
	 * @param redeemId 要刪除的Redeem的ID
	 */
	@DeleteMapping("/{redeemId}")
	public Redeem softDeleteRedeem(@PathVariable Integer redeemId) {
		return redeemService.softDeleteRedeem(redeemId);
		
	};

	/**
	 * 根據Redeem狀態查詢對應的所有Redeem紀錄
	 * @param redeem_status Redeem的狀態
	 * @return 返回符合狀態條件的Redeem紀錄列表
	 */
	@GetMapping("/redeemStatus")
	public List<Redeem> findByRedeemStatus(@RequestParam String redeemStatus){
		return redeemService.findByRedeemStatus(redeemStatus);
	};
	/**
	 * 根據訂單成立時間查詢訂單列表
	 * @param redeemDate
	 * @return redeems
	 */
	@GetMapping("/redeemDate")
	public List<Redeem> findByTime(@RequestParam Date redeemDate){
		return redeemService.findByTime(redeemDate);
	};
	/**
	 * 根據會員ID查詢訂單列表
	 * @param memberId
	 * @return redeems
	 */
	@GetMapping("/memberId")
	public List<Redeem> findByMemberId(@RequestParam Integer memberId){
		return redeemService.findByMemberId(memberId);
	};
	
	@PutMapping("/{redeemId}/cancelRedeem")
	public Redeem cancelRedeem(@PathVariable Integer redeemId) {
		return redeemService.cancelRedeem(redeemId);
	}
	
	
	
}
