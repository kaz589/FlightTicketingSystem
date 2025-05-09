package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.demo.model.Products;
import com.demo.model.Redeem;
import com.demo.model.RedeemItem;

//商品管理
public interface IProductsService {

	/**
	 * 根據商品ID查詢商品
	 * 
	 * @param id 產品ID
	 * @return 查詢到的商品實體
	 */
	Optional<Products> findProductById(Integer id);

	

	/**
	 * 查詢所有商品
	 * 
	 * @return 所有商品的實體列表
	 */
	List<Products> findAllProducts();

	/**
	 * 根據商品名稱查詢商品
	 * 
	 * @param product_name 商品名稱
	 * @return 以商品名稱查詢到的商品實體列表
	 */
	List<Products> findProductsByName(String name);

	/**
	 * 查詢低庫存商品
	 * 
	 * @param threshold 庫存閾值
	 * @return 庫存量低於指定閾值的商品實體列表
	 */
	List<Products> findLowStockProducts(Integer threshold);

	/**
	 * 新增商品
	 * 
	 * @param product_name  商品名稱
	 * @param needmiles     所需里程
	 * @param quantity      商品數量
	 * @param product_image 商品圖片
	 * @return 新增後的商品實體
	 */
	Products saveProduct(Products product);

	/**
	 * 根據商品ID刪除商品
	 * 
	 * @param id 商品ID
	 */
	boolean deleteProductById(Integer id);

	/**
	 * 根據商品ID更新商品
	 * 
	 * @param id             商品ID
	 * @param updateProducts 更新的商品實體
	 * @return 更新後的商品實體
	 */
	Products updateProductById(Integer id, Products product);

	/**
	 * 根據里程範圍查詢商品
	 * 
	 * @param min 最少需要的里程數
	 * @param max 最多需要的里程數
	 * @return 符合里程條件的商品列表
	 */
	List<Products> findProductsByNeedmiles(Integer min, Integer max);

	/**
	 * 根據訂單扣除某商品數量
	 * 
	 * @param productId
	 * @param decreaseQuantity
	 * @return 更新後的商品
	 */
	Products decreaseStock(Integer productId, Integer decreaseQuantity);
	
	/**
	 * 增加某商品數量
	 * @param productId
	 * @param increaseQuantity
	 * @return 更新後的商品
	 */
	Products increaseStock(Integer productId, Integer increaseQuantity);


	 Products getProductById(Integer id) ;






		

	/**
	 * @param categoryId
	 * @return
	 */
//	Category findProductsByCategoryId(Integer categoryId);
	

	
}