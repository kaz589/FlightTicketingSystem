package com.demo.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.model.Products;
import com.demo.repository.ProductsRepository;
import com.demo.service.IProductsService;


@Service
public class ProductsImp implements IProductsService {
	
	@Autowired
	private ProductsRepository pruductsRepo;

	
	@Override
	public Page<Products> getPagedMessages(int page, int size) {
		Page<Products> pageResult =  pruductsRepo.findAll(
				PageRequest.of(page, // 查詢的頁數，從0起算
						size, // 查詢的每頁筆數
						Sort.by("product_id"))); // 依 airportsId 欄位降冪排序

		// 直接返回 pageResult
		return pageResult;
	}

	@Override
	public Products findProductsById(Integer productId) {
		Products product =  pruductsRepo.findProductsById(productId);
		
		return product;

	}

	@Override
	public List<Products> findAllProducts() {
		List<Products> allProducts =  pruductsRepo.findAll();
		return allProducts;
	}

	@Override
	public List<Products> findProductsByName(String name) {
		List<Products> result =  pruductsRepo.findProductsByName(name);
		return result;
	}

	@Override
	public List<Products> findLowStockProducts(Integer threshold) {
	
		return 	 pruductsRepo.findLowStockProducts(threshold);
	}


	@Override
	public List<Products> findProductsByNeedmiles(Integer needmiles) {
		return  pruductsRepo.findProductsByNeedmiles(needmiles);
	}		

	@Override
	public Products addProducts(Products addproduct) {
		return  pruductsRepo.save(addproduct);
	}		

	@Override
	public void deleteProductsById(Integer Id) {
		 pruductsRepo.deleteById(Id);

	}

	@Override
	public Products updateProductsById(Integer Id, Products updateProduct) {
		return  pruductsRepo.save(updateProduct);
	}

	
}
