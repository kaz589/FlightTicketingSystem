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
	private ProductsRepository productsRepo;

	
	
	@Override
	public Products findProductsById(Integer productId) {
		Products product =  productsRepo.findById(productId).orElse(null);;
		
		return product;

	}

	@Override
	public List<Products> findAllProducts() {
		List<Products> allProducts =  productsRepo.findAll();
		return allProducts;
	}

	@Override
	public List<Products> findProductsByName(String name) {
		List<Products> result =  productsRepo.findByNameContaining(name);
		return result;
	}

	@Override
	public List<Products> findLowStockProducts(Integer threshold) {
	
		return 	 productsRepo.findLowStockProducts(threshold);
	}


		

	@Override
	public Products save(Products addproduct) {
		return  productsRepo.save(addproduct);
	}		

	@Override
	public void deleteProductsById(Integer Id) {
		 productsRepo.deleteById(Id);

	}

	@Override
	public Products updateProductsById(Integer Id, Products updateProduct) {
		return  productsRepo.save(updateProduct);
	}

	@Override
	public List<Products> findProductsByNeedmiles(Integer min ,Integer max) {
	
		return productsRepo.findProductsByNeedmiles(min, max);
	}

//	@Override
//	public Page<Products> getPagedProducts(int page, int size) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
}
