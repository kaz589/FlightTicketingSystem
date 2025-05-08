package com.demo.service;

import java.util.List;
import java.util.Optional;

import com.demo.model.Category;

public interface ICategoryService {
	/**
	 * 獲取所有類別
	 * @return 類別
	 */
	List<Category> findAllCategories();
	  /**
     * 新增類別
     * @param category 類別實體
     * @return 新增後的類別實體
     */
	Category  addCategory(Category category);
	
   
	
	
	
	
}
