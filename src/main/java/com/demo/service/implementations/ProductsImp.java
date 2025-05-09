package com.demo.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.model.Category;
import com.demo.model.Products;
import com.demo.model.RedeemItem;
import com.demo.repository.ProductsRepository;
import com.demo.service.IProductsService;

import jakarta.validation.Valid;

@Service
public class ProductsImp implements IProductsService {

	@Autowired
	private ProductsRepository productsRepo;
	
	@Override
	public Products getProductById(Integer id) {
		 return productsRepo.findById(id).orElse(null);
	}
	@Override
	public Optional<Products> findProductById(Integer id) {
		return productsRepo.findById(id);
	}

	@Override
	public List<Products> findAllProducts() {
		return productsRepo.findAll();
	}

	@Override
	 public List<Products> findProductsByName(String name) {
        return productsRepo.findByNameContaining(name);
    }

	@Override
	public List<Products> findLowStockProducts(Integer threshold) {
		return productsRepo.findLowStockProducts(threshold);
	}

	@Override
	public Products saveProduct(Products addproduct) {
		return productsRepo.save(addproduct);
	}

	@Override
	public boolean deleteProductById(Integer Id) {
		productsRepo.deleteById(Id);
		return true;
	}
	
///更新商品XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	@Override
	public Products updateProductById(Integer id, @Valid Products product) {
		if (!productsRepo.existsById(id)) {
			throw new RuntimeException("無法更新，找不到 ID 為 " + id + " 的商品");
		}
		product.setId(id);
		return productsRepo.save(product);
	}

	@Override
	public List<Products> findProductsByNeedmiles(Integer min, Integer max) {

		return productsRepo.findProductsByNeedmiles(min, max);
	}

	@Override
	public Products decreaseStock(Integer productId,Integer decreaseQuantity) {
		Products product = productsRepo.findById(productId)
				.orElseThrow(() -> new RuntimeException("無法更新，找不到 ID 為 " + productId + " 的商品"));

		int currentStock = product.getQuantity();
		
		if (decreaseQuantity <= 0) {
			throw new IllegalArgumentException("庫存數量必須大於 0。");
		}
		if (currentStock < decreaseQuantity) {
			throw new RuntimeException("庫存不足，無法兌換。");
		}
		product.setQuantity(currentStock - decreaseQuantity);
		return productsRepo.save(product);
	}

	@Override
	public Products increaseStock(Integer productId, Integer increaseQuantity) {
	    Products product = productsRepo.findById(productId)
	            .orElseThrow(() -> new RuntimeException("無法更新，找不到 ID 為 " + productId + " 的商品"));

	    if (increaseQuantity <= 0) {
	        throw new IllegalArgumentException("增加的庫存數量必須大於 0。");
	    }

	    int currentStock = product.getQuantity();
	    product.setQuantity(currentStock + increaseQuantity);

	    return productsRepo.save(product);
	}
	


}