package com.example.custhy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.custhy.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
    List<Product>findByProductOrigin(String productOrigin);
	
	
//	List<ResponseByCategory> findByCategory(String category);
	
	
	
	@Query("SELECT p FROM Product p WHERE p.productName=:name")
	Product getByName(String name);

}
