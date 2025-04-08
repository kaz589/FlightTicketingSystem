package com.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.model.Category;
import com.demo.model.Products;
import com.demo.service.IProductsService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 
 */
@RestController
@RequestMapping("/api/products")
public class ProductsController {

	@Autowired
	private IProductsService iProductsService;

	/**
	 * 根據商品ID查詢商品。
	 * 
	 * @param id 商品的ID
	 * @return 產品的詳細資料
	 */
	@GetMapping("/{id}")
	public Products findProductById(@PathVariable("id") Integer id) {

		Products product = iProductsService.findProductsById(id);
		return product;
	}

	/**
	 * 查詢所有商品。
	 * 
	 * @return 所有商品的列表
	 */

	@GetMapping("/alls")
	public List<Products> findAllProducts() {
		List<Products> products = iProductsService.findAllProducts();

		return products;
	}

	// 根據商品名稱查詢商品

	@GetMapping("/name")
	public List<Products> findProductsByName(@RequestParam("name") String name) {
		List<Products> products = iProductsService.findProductsByName(name);
		if (products.isEmpty()) {
			return products;
		}
		return products;
	}

	// 查詢低庫存商
	@GetMapping("/lowstock")
	public List<Products> searchlowstock(@RequestParam("threshold") Integer threshold) {
		List<Products> products = iProductsService.findLowStockProducts(threshold);
		;
		if (products.isEmpty()) {
			return products;
		}
		return products;
	}

	// 根據需要的里程查詢商品

	@GetMapping("/needmiles")
	public List<Products> searchByNeedmiles(@RequestParam("needmiles") Integer needmiles) {
		List<Products> products = iProductsService.findProductsByNeedmiles(needmiles);
		;
		if (products.isEmpty()) {
			return products;
		}
		return products;
	}

	/**
	 * 新增一個商品。
	 * 
	 * @param products 新商品的詳細資料
	 * @return 新增的商品資料
	 */
	@PostMapping
	public Products addProducts(@RequestBody Map<String, Object> payload) {
	    Products product = new Products();
	    product.setName((String) payload.get("name"));
	    product.setDesc((String) payload.get("desc"));
	    product.setNeedmiles((Integer) payload.get("needmiles"));
	    product.setQuantity((Integer) payload.get("quantity"));
	    product.setImage((String) payload.get("image"));

	    Integer categoryId = (Integer) payload.get("categoryId");
	    Category category = new Category();
	    category.setCategoryId(categoryId); // 假設有 setId()
	    product.setCategory(category);

	    return iProductsService.addProducts(product);
	}

	/**
	 * 刪除指定ID的商品。
	 * 
	 * @param productId 商品的ID
	 * @return HTTP響應狀態
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProducts(@PathVariable("id") Integer productId) {
		iProductsService.deleteProductsById(productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 更新指定ID商品的詳細資料。
	 * 
	 * @param productId      商品ID
	 * @param updatedProduct 更新後的商品資料
	 * @return 更新後的商品資料
	 * @throws ResponseStatusException 如果商品ID不一致，拋出錯誤
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Products> updateProduct(@PathVariable("id") Integer productId,
			@RequestBody Products updatedProduct) {
		// 確保 ID 一致，防止數據不匹配
		if (!productId.equals(updatedProduct.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "商品 ID 不一致");
		}
		// 調用服務層更新方法
		Products updated = iProductsService.updateProductsById(productId, updatedProduct);

		return new ResponseEntity<>(updated, HttpStatus.OK);

	}
}
