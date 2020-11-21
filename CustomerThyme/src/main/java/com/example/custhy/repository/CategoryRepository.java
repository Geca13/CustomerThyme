package com.example.custhy.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.custhy.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Boolean existsByProductCategory(String productCategory);
	
	Category findByProductCategory(String productCategory);
	
	//@Query("SELECT c FROM Category c WHERE c.productCategory=:category")
//	List<ResponseByCategory> findByCategory(String category);
	
}
