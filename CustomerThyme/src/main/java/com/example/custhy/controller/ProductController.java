package com.example.custhy.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.custhy.entity.Product;
import com.example.custhy.repository.ProductRepository;
import com.example.custhy.services.ProductServices;

@Controller
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductServices productServices;
	
	@GetMapping("/product")
	public String viewProductPage(Model model) {
		
		 findPages(1,"productName", "asc", model);
		 
		 return "all_products";
		
	}
	
	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute ("product") Product product) {
		
		productServices.save(product);
		
		return "redirect:/product"; 
		
	}
	
	@GetMapping("/showNewProductForm")
	public String showNewProductForm (Model model) {
		
		Product product = new Product();
		model.addAttribute("product", product);
		
		return "new_product";
	}
	
	@GetMapping("/showUpdatingForm/{id}")
	public String showUpdatingForm(@PathVariable("id")Integer id, Model model) {
		Product product = productRepository.findById(id).get();
		model.addAttribute("product",product);
		return "update_product";
		
	}
	
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable ("id")Integer id, Product product) {
		
		productRepository.deleteById(id);
		
		return "redirect:/product";
	}
	
	@GetMapping("pages/{pageNumbers}")
	public String findPages(@PathVariable("pageNumbers") Integer pageNumbers,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		
		Integer pageSize = 2;
		
		Page<Product>pages = productServices.findPaginated(pageNumbers, pageSize, sortField, sortDir);
		
		List<Product> listProducts = pages.getContent();
		model.addAttribute("currentPage",pageNumbers);
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalItems", pages.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("listProducts", listProducts);
		return "all_products";
		
	}
	
	
}
