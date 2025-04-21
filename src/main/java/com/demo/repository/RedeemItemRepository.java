package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.model.RedeemItem;

@Repository
public interface RedeemItemRepository extends JpaRepository<RedeemItem,Integer> {
//查看訂單明細
	  List<RedeemItem> findRedeemItemByRedeem_RedeemId(Integer redeemId);

}
