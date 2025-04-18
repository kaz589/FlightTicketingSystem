package com.demo.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.demo.model.Products;
import com.demo.service.IProductsService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/products")
public class ProductsController {

	@Autowired
	private IProductsService productsService;

	/**
	 * 根據商品ID查詢商品。
	 * 
	 * @param id 商品的ID
	 * @return 產品的詳細資料
	 */
	@GetMapping("/{id}")
	public Products findProductById(@PathVariable("id") Integer id) {
		return productsService.findProductById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "商品不存在"));
	}

	/**
	 * 查詢所有商品。
	 * 
	 * @return 所有商品的列表
	 */

	@GetMapping("/all")
	public List<Products> findAllProducts() {
		List<Products> products = productsService.findAllProducts();
		return checkNotEmpty(products, "沒有商品資料");
	}

	/**
	 * @param name
	 * @return 查詢到符合名稱的商品的列表
	 */
	@GetMapping("/name")
	public List<Products> findProductsByName(@RequestParam("name") String name) {
		List<Products> products = productsService.findProductsByName(name);
		return checkNotEmpty(products, "沒有商品資料");
	}

	/**
	 * 查詢低庫存商品
	 * 
	 * @param threshold
	 * @return 庫存低於threshold的商品列表
	 */
	@GetMapping("/lowstock")
	public List<Products> searchLowStock(@RequestParam("threshold") Integer threshold) {
		List<Products> products = productsService.findLowStockProducts(threshold);
		return checkNotEmpty(products, "沒有商品資料");
	}

	/**
	 * 根據里程範圍查詢商品
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	@GetMapping("/needmiles")
	public List<Products> searchByNeedmiles(
			@RequestParam(name = "min", required = false, defaultValue = "0") Integer min,
			@RequestParam(name = "max", required = false, defaultValue = "999999") Integer max) {
		List<Products> products = productsService.findProductsByNeedmiles(min, max);
		return checkNotEmpty(products, "沒有商品資料");
	}

	/**
	 * 新增一個商品。
	 * 
	 * @param products 新商品的詳細資料
	 * @return product 新增的商品資料
	 */
	@PostMapping
	public Products create(@RequestBody @Valid Products product) {

		return productsService.saveProduct(product);

	}

	/**
	 * 刪除一個商品
	 * 
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		productsService.findProductById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "商品不存在"));

		productsService.deleteProductById(id);
	}

	/**
	 * 更新指定ID商品的詳細資料。
	 * 
	 * @param id      商品ID
	 * @param product 更新後的商品資料
	 * @return 更新後的商品資料
	 */

	@PutMapping("/{id}")
	public Products update(@PathVariable Integer id, @RequestBody @Valid Products product) {
		Optional<Products> existingProduct = productsService.findProductById(id);
		if (existingProduct.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "商品不存在");
		}
		return productsService.updateProductById(id, product);
	}

	/**
	 * 檢查有無商品並回傳訊息
	 * 
	 * @param <T>
	 * @param list
	 * @param errorMessage
	 * @return 查詢到的商品列表
	 */
	private <T> List<T> checkNotEmpty(List<T> list, String errorMessage) {
		if (list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
		}
		return list;
	}


	@PostMapping("/{productId}/uploadImage")
	public ResponseEntity<Map<String, String>>  uploadProductImage(@PathVariable("productId") Integer id,
			@RequestParam("image") MultipartFile file)
	            throws IOException {
	        if (file.isEmpty()) {
	            return ResponseEntity.badRequest().body(Map.of("error", "Empty file"));
	        }

	        File dir = new File("uploads/ProductsImage/");
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

	        String filename = file.getOriginalFilename();
	        Path filepath = Paths.get("uploads/ProductsImage/", filename);
	        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

	        String imageUrl = "/ProductsImage/" + filename;

	        return ResponseEntity.ok(Map.of("url", imageUrl));
	    }

}