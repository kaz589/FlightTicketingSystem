package com.demo.service.implementations;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Member;
import com.demo.model.Products;
import com.demo.model.Redeem;
import com.demo.model.RedeemItem;
import com.demo.model.DTO.CreateRedeemRequest;
import com.demo.model.DTO.RedeemItemDTO;
import com.demo.repository.MemberRepository;
import com.demo.repository.ProductsRepository;
import com.demo.repository.RedeemItemRepository;
import com.demo.repository.RedeemRepository;
import com.demo.service.IProductsService;
import com.demo.service.IRedeemService;
import com.demo.service.MemberService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class RedeemImp implements IRedeemService {

	@Autowired
	private RedeemRepository redeemRepo;

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private RedeemItemRepository redeemItemRepo;

	@Autowired
	private ProductsRepository productsRepo;
	
	@Autowired
	private ProductsImp productsService;
	
	@Autowired
	private MemberService memberService;
	
//	訂單預設狀態
	private static final String INITIAL_STATUS = "處理中";

	private static final Map<String, Set<String>> ALLOWED_STATUS_TRANSITIONS = Map.of("待處理", Set.of("處理中", "已取消"),
			"處理中", Set.of("已完成", "已取消", "待出貨"), "待出貨", Set.of("已出貨", "已取消"), "已出貨", Set.of("已送達"), "已送達", Set.of("已完成"),
			"已取消", Set.of(), "已完成", Set.of());

//查詢全部訂單訂單
	@Override
	public List<Redeem> getAllRedeem() {
		return redeemRepo.findAll();
	}

//根據會員ID查詢訂單
	@Override
	public List<Redeem> findByMemberId(Integer memberId) {
		return redeemRepo.findByMemberId(memberId);
	}

//根據RedeemId查詢訂單
	@Override
	public Redeem getRedeemById(Integer redeemId) {
		return redeemRepo.findById(redeemId).orElse(null);
	}

//根據時間查詢訂單
	@Override
	public List<Redeem> findByTime(Date date) {
		return redeemRepo.findByCreateAt(date);
	}

//	新增訂單
	@Override
	@Transactional
	public Redeem addRedeem(CreateRedeemRequest createRedeemRequest) {
		Redeem redeem = new Redeem();
		redeem.setRedeemStatus(INITIAL_STATUS); // 設定初始狀態

		Integer memberId = createRedeemRequest.getMemberId();
		List<RedeemItemDTO> redeemItemsDTO = createRedeemRequest.getRedeemItems();

		//檢查會員存在	
		Member member = memberRepo.findById(memberId)
				.orElseThrow(() -> new RuntimeException("找不到 ID 為 " + memberId + " 的會員"));

		redeem.setMember(member);
		Redeem savedRedeem = redeemRepo.save(redeem);

		int totalMilesNeeded = 0; //計算總里程

		for (RedeemItemDTO itemDTO : redeemItemsDTO) {
			// 檢查商品存在
			Products product = productsRepo.findById(itemDTO.getProductId())
					.orElseThrow(() -> new RuntimeException("找不到 ID 為 " + itemDTO.getProductId() + " 的商品"));
			// 庫存檢查
			if (product.getQuantity() < itemDTO.getQuantity()) {
				throw new IllegalArgumentException(String.format("商品 ID %d '%s' 庫存不足：需要 %d，目前庫存 %d",
						itemDTO.getProductId(), product.getName(), itemDTO.getQuantity(), product.getQuantity()));
			}
			int needmiles = product.getNeedmiles();
			int requiredForThisItem = needmiles * itemDTO.getQuantity();
			totalMilesNeeded += requiredForThisItem;
			
			
			// 檢查里程足夠(待與孟儒討論後決定)
//			int needmiles = product.getNeedmiles();
//			int required = needmiles * itemDTO.getQuantity();
//			int available = member.getRemainingMiles();
//
//			if (available < required) {
//				throw new IllegalArgumentException(String.format("里程不足：共需要 %d里程，目前帳號剩餘 %d里程", required, available));
//			}

			// 創建redeem_item物件，設定關聯的redeem product needmiles quantity
			RedeemItem redeemItem = new RedeemItem();
			redeemItem.setRedeem(savedRedeem);
			redeemItem.setProduct(product);
			redeemItem.setUsedMiles(needmiles);
			redeemItem.setQuantity(itemDTO.getQuantity());

			// 保存 Redeem_item
			redeemItemRepo.save(redeemItem);

			// 扣減庫存
			productsService.updateStockAfterOrder(itemDTO.getProductId(), itemDTO.getQuantity());

			// 扣除會員里程
			memberService.decreaseMilesById(memberId, needmiles);
//			member.setRemainingMiles(available - required);
//			memberRepo.save(member);
		}
		  return savedRedeem;
	}

//根據RedeemId軟刪除訂單
	@Override
	public void deleteRedeem(Integer redeemId) {
		Redeem redeem = redeemRepo.findById(redeemId)
				.orElseThrow(() -> new RuntimeException("無法刪除，找不到 ID 為 " + redeemId + " 的兌換訂單"));
		redeem.setDeleted(true);
		redeemRepo.save(redeem);

	}

//根據訂單狀態查詢訂單
	@Override
	public List<Redeem> findByStatus(String redeem_status) {
		return redeemRepo.findByStatus(redeem_status);
	}

//	更新訂單
	@Override
	public Redeem updateRedeem(Integer redeemId, @Valid Redeem redeem) {
		Redeem existingRedeem = redeemRepo.findById(redeemId)
				.orElseThrow(() -> new RuntimeException("無法更新，找不到 ID 為 " + redeemId + " 的訂單"));

		if ("完成".equals(existingRedeem.getRedeemStatus())) {
			throw new RuntimeException("訂單已完成，無法修改");
		}
		redeem.setRedeemId(redeemId);
		return redeemRepo.save(redeem);
	}

//更新訂單狀態
	public Redeem updateRedeemStatus(Integer redeemId, String newStatus) {
		// 確認訂單存在
		Redeem existingRedeem = redeemRepo.findById(redeemId)
				.orElseThrow(() -> new RuntimeException("找不到 ID 為 " + redeemId + " 的訂單"));
		// 拿到這筆訂單的狀態
		String currentStatus = existingRedeem.getRedeemStatus();
		// 如果狀態相同或當前狀態為 null，則不進行任何更改
		if (currentStatus != null && !currentStatus.equals(newStatus)) {
			// 把目前狀態可以轉換的目標狀態找出來
			Set<String> allowedTransitioins = ALLOWED_STATUS_TRANSITIONS.getOrDefault(currentStatus, Set.of());
			if (!allowedTransitioins.contains(newStatus)) {
				throw new RuntimeException(String.format("訂單狀態從 '%s' 無法轉換到 '%s'", currentStatus, newStatus));
			}
			existingRedeem.setRedeemStatus(newStatus);
			return redeemRepo.save(existingRedeem);
		}
		return existingRedeem;
	}

}
