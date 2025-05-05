package com.demo.service.implementations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.demo.model.Member;
import com.demo.model.Products;
import com.demo.model.Redeem;
import com.demo.model.RedeemItem;
import com.demo.model.DTO.RedeemDTO;
import com.demo.model.DTO.RedeemItemDTO;
import com.demo.repository.MemberRepository;
import com.demo.repository.ProductsRepository;
import com.demo.repository.RedeemItemRepository;
import com.demo.repository.RedeemRepository;
import com.demo.service.IRedeemService;
import com.demo.service.MailService;
import com.demo.service.MemberService;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
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
	
	@Autowired
	private MailService mailService;
	
//	訂單預設狀態
	private static final String INITIAL_STATUS = "處理中";
	private static final String SHIPPED = "已出貨";
	private static final String CANCELLED="已取消";
	private static final String FINESHED="已完成";

	private static final Map<String, Set<String>> ALLOWED_STATUS_TRANSITIONS = Map.of(
			"待處理", Set.of("處理中", "已取消"),
			"處理中", Set.of("已完成", "已取消", "待出貨"),
			"待出貨", Set.of("已出貨", "已取消"),
			"已出貨", Set.of("已送達"),
			"已送達", Set.of("已完成"),
			"已取消", Set.of(),
			"已完成", Set.of());

//存在性驗證
	private <T> List<T> checkNotEmpty(List<T> list, String errorMessage) {
		if (list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
		}
		return list;
	}
//查詢全部訂單訂單
	@Override
	public List<Redeem> getAllRedeem() {
		return redeemRepo.findAll();
	}

//根據會員ID查詢訂單
	@Override
	public List<Redeem> findByMemberId(Integer memberId) {
		List<Redeem> redeems = redeemRepo.findByMember_MemberId(memberId);
		 return checkNotEmpty(redeems, "此會員尚無兌換紀錄");
	}

//根據RedeemId查詢訂單
	@Override
	public Redeem getRedeemById(Integer redeemId) {
		 return redeemRepo.findById(redeemId)
			        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此筆兌換紀錄"));
	}

//根據時間查詢訂單
	@Override
	public List<Redeem> findByTime(Date date) {
		List<Redeem> redeems =	redeemRepo.findByCreateAt(date);
		 return checkNotEmpty(redeems, "此時間尚無兌換紀錄");
	}

//	新增訂單
	@Override
	@Transactional
	public Redeem addRedeem(RedeemDTO createRedeemRequest) {
		Redeem redeem = new Redeem();
		redeem.setRedeemStatus(INITIAL_STATUS); // 設定初始狀態

		Integer memberId = createRedeemRequest.getMemberId();
		List<RedeemItemDTO> redeemItemsDTO = createRedeemRequest.getRedeemItems();

		//檢查會員存在	
		Member member = memberRepo.findById(memberId)
				.orElseThrow(() -> new RuntimeException("找不到 ID 為 " + memberId + " 的會員"));

		redeem.setMember(member);
		redeem = redeemRepo.save(redeem);
		
		int totalMilesNeeded = 0; //計算總里程
		List<RedeemItem> newRedeemItems= new ArrayList<>();
		//遍歷本訂單的商品項目
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

			// 創建redeem_item物件，設定關聯的redeem product needmiles quantity
			RedeemItem redeemItem = new RedeemItem();
			redeemItem.setRedeem(redeem);
			redeemItem.setProduct(product);
			redeemItem.setUsedMiles(needmiles);
			redeemItem.setQuantity(itemDTO.getQuantity());
			
			newRedeemItems.add(redeemItem);
			// 保存 Redeem_item
			

			// 扣減庫存
			productsService.decreaseStock(itemDTO.getProductId(), itemDTO.getQuantity());

		}
		redeemItemRepo.saveAll(newRedeemItems);
		
		// 扣除會員里程
		memberService.decreaseMilesById(memberId, totalMilesNeeded);
		redeem.setRedeemTotalMiles(totalMilesNeeded);
		//寄信
	String customerName =member.getFullName();
	String email =member.getEmail();
	String redeemId=redeem.getRedeemId().toString();
	Integer AA=totalMilesNeeded;
try {
	mailService.sendRedeemMail(email, customerName, redeemId, AA.toString());
} catch (MessagingException e) {
	
	e.printStackTrace();
	System.out.println(e);
}
		
//		redeemRepo.save(redeem);
		return redeemRepo.save(redeem);
		
	}

//根據RedeemId軟刪除訂單
	@Override
	public Redeem softDeleteRedeem(Integer redeemId) {
		Redeem redeem = redeemRepo.findById(redeemId)
				.orElseThrow(() -> new RuntimeException("無法刪除，找不到 ID 為 " + redeemId + " 的兌換訂單"));
		redeem.setDeleted(true);
		 return redeemRepo.save(redeem);
	}

//根據訂單狀態查詢訂單
	@Override
	public List<Redeem> findByRedeemStatus(String redeemStatus) {
		return redeemRepo.findByRedeemStatus(redeemStatus);
	}

//	更新訂單
	@Override
	public Redeem updateRedeem(Integer redeemId, @Valid Redeem redeem) {
		Redeem existingRedeem = redeemRepo.findById(redeemId)
				.orElseThrow(() -> new RuntimeException("無法更新，找不到 ID 為 " + redeemId + " 的訂單"));
		if ("已出貨".equals(existingRedeem.getRedeemStatus())) {
			throw new IllegalStateException("訂單已出貨，無法修改");
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
				throw new IllegalStateException(String.format("訂單狀態從 '%s' 無法轉換到 '%s'", currentStatus, newStatus));
			}
			existingRedeem.setRedeemStatus(newStatus);
			return redeemRepo.save(existingRedeem);
		}
		return existingRedeem;
	}
	
	/**
	 * 取消訂單
	 * @param redeemId
	 * @return Redeem
	 */
	public	Redeem cancelRedeem(Integer redeemId) {
		Redeem cancelRedeem  = redeemRepo.findById(redeemId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"找不到 ID 為 " + redeemId + " 的訂單"));
				if (SHIPPED.equals(cancelRedeem.getRedeemStatus())) {
					 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "訂單已出貨，無法取消");
				}
				//設訂單狀態為已取消
				cancelRedeem.setRedeemStatus(CANCELLED);
				//軟刪除訂單
				cancelRedeem.setDeleted(true);
				redeemRepo.save(cancelRedeem); // 保存狀態更新

				int totalIncreaseMiles = 0;
				List<RedeemItem> redeemItems = cancelRedeem.getRedeemItems(); // 需要您在 Redeem 實體中建立 RedeemItem 的關聯
				
				int memberid = cancelRedeem.getMember().getMemberId();
				
				//	拿到商品項目
				for(RedeemItem redeemItem : redeemItems) {
					Products product = productsRepo.findById(redeemItem.getProduct().getId())
							.orElseThrow(() -> new RuntimeException("找不到 ID 為 " + redeemItem.getProduct().getId() + " 的商品"));
					
					int needmiles = product.getNeedmiles();
					int requiredForThisItem = needmiles * redeemItem.getQuantity();
					totalIncreaseMiles += requiredForThisItem;
					// 商品增加庫存
					productsService.increaseStock(product.getId(), redeemItem.getQuantity());
				}
				// 會員增加里程
				memberService.increaseMilesById(memberid, totalIncreaseMiles);
				return cancelRedeem;
	}

	}
	

