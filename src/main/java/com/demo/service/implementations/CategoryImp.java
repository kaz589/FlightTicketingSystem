package com.demo.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Category;
import com.demo.repository.CategoryRepository;
import com.demo.service.ICategoryService;


@Service
public class CategoryImp implements ICategoryService {

	@Autowired
	private CategoryRepository cRepo;

	@Override
	public List<Category> findAllCategories() {
		List<Category> allCategories = cRepo.findAll();
		return allCategories;
	}

	@Override
	public Category addCategory(Category category) {

		return cRepo.save(category);
	}

	@Override
	public void deleteCategoryById(Integer categoryId) {
		cRepo.deleteById(categoryId);
	}

}
