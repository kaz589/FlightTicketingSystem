package com.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer>{

	List<Products> findByNameContaining(String Name);
	
	@Query("from Products p where p.quantity<:threshold ")
	List<Products> findLowStockProducts(@Param("threshold")Integer threshold) ;
	
	@Query("from Products p where p.needmiles between :minNeedmiles and :maxNeedmiles")
	List<Products> findProductsByNeedmiles(
			@Param("minNeedmiles") Integer minNeedmiles, 
			@Param("maxNeedmiles") Integer maxNeedmiles);
}
