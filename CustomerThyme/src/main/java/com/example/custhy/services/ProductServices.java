package com.example.custhy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.custhy.entity.Category;
import com.example.custhy.entity.Manufacturer;
import com.example.custhy.entity.Product;
import com.example.custhy.repository.CategoryRepository;
import com.example.custhy.repository.ManufacturerRepository;
import com.example.custhy.repository.ProductRepository;

@Service
public class ProductServices {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ManufacturerRepository manufacturerRepo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
public Page<Product> findPaginated(Integer pageNumber, Integer pageSize,String sortField, String sortDirection){
		
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
		
		return productRepository.findAll(pageable);
	}

         public void save(Product product) {
	
        	 if(manufacturerRepo.existsByManufactorerName(product.getManufacturer().getManufactorerName())) {
     			
     			Manufacturer man = manufacturerRepo.findByManufactorerName(product.getManufacturer().getManufactorerName());
     			
     			product.setManufacturer(man);
     		}
     		
     		if(categoryRepo.existsByProductCategory(product.getCategory().getProductCategory())) {
     			
     			Category cat = categoryRepo.findByProductCategory(product.getCategory().getProductCategory());
     			
     			product.setCategory(cat);
     		}
     		
     		 productRepository.save(product);
	
}
}
