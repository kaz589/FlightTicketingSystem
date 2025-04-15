package com.demo.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Products;
import com.demo.model.Redeem_item;
import com.demo.repository.ProductsRepository;
import com.demo.service.IProductsService;

import jakarta.validation.Valid;

@Service
public class ProductsImpl implements IProductsService {

	@Autowired
	private ProductsRepository productsRepo;
	@Autowired
	private Redeem_item redeem_item;

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
	public Products updateStockAfterOrder(Integer id, Redeem_item redeem_item) {
		Products product = productsRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("無法更新，找不到 ID 為 " + id + " 的商品"));

		int currentStock = product.getQuantity();
		int redeemQuantity = redeem_item.getQuantity();
		if (redeemQuantity <= 0) {
			throw new IllegalArgumentException("兌換數量必須大於 0。");
		}
		if (currentStock < redeemQuantity) {
			throw new RuntimeException("庫存不足，無法兌換。");
		}
		product.setQuantity(currentStock - redeemQuantity);
		return productsRepo.save(product);
	}

}
