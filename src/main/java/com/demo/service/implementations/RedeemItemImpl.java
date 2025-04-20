package com.demo.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Member;
import com.demo.model.Products;
import com.demo.model.Redeem;
import com.demo.model.RedeemItem;
import com.demo.repository.ProductsRepository;
import com.demo.repository.RedeemItemRepository;
import com.demo.repository.RedeemRepository;
import com.demo.service.IRedeemItemService;


@Service
public class RedeemItemImpl implements IRedeemItemService {


	@Autowired
	private RedeemItemRepository redeemItemRepo;

	@Autowired
	private ProductsRepository productsRepo;

	@Autowired
	private RedeemRepository redeemRepo;
	



	// 刪除訂單項目
    @Override
    public boolean deletedRedeemItem(Integer redeemItemId) {
        try {
            redeemItemRepo.deleteById(redeemItemId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("無法刪除，找不到 ID 為 " + redeemItemId + " 的訂單項目");
        }
    }

//查看訂單明細
	@Override
	public List<RedeemItem> findRedeemItemByRedeemId(Integer redeemId) {
		List<RedeemItem> redeemItems = redeemItemRepo.findRedeemItemByRedeemId(redeemId);
        if (redeemItems.isEmpty()) {
            throw new RuntimeException("找不到訂單編號為 " + redeemId + " 的訂單項目");
        }
        return redeemItems;
    }
//更新訂單項目數量
	@Override
	public RedeemItem updateQuantity(Integer redeemItemId, Integer newQuantity) {
		RedeemItem redeemItem = redeemItemRepo.findById(redeemItemId)
				.orElseThrow(() -> new RuntimeException("無法更新，找不到 ID 為 " + redeemItemId + " 的訂單項目"));
		redeemItem.setQuantity(newQuantity);
		return redeemItemRepo.save(redeemItem);
	}

}
