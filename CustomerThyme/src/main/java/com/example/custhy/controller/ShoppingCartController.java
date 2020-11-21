package com.example.custhy.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

import com.example.custhy.entity.Customer;
import com.example.custhy.entity.Product;
import com.example.custhy.entity.ShoppingCart;
import com.example.custhy.repository.CustomerRepository;
import com.example.custhy.repository.ProductRepository;
import com.example.custhy.repository.ShoppingCartRepository;
import com.example.custhy.services.ShoppingCartServices;

@Controller
public class ShoppingCartController {
	
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ShoppingCartServices shopServices;
	
	@Autowired
	ShoppingCartRepository shopRepository;
	
    
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/shoppingCart")
	public String viewShoppingCartPage(Model model) {
		
		 findPagina(1,"scId", "asc", model);
		 
		 return "all_shopping_carts";
		
	}
	
	@PostMapping("/saveShoppingCart")
	public String saveShoppingCart(@ModelAttribute ("shoppingCart") ShoppingCart shoppingCart) {
		
		
		shopRepository.save(shoppingCart);
		
		return "redirect:/shoppingCart";
	}
	
	@GetMapping("/showNewShoppingCartForm/{id}")
	public String showNewShoppingCartForm (@PathVariable("id")Integer id, Model model ) {
		
		ShoppingCart shoppingCart = new ShoppingCart();
		Date date = new Date();
		Customer customer = customerRepository.findById(id).get();
		List<Product>products = productRepository.findAll();
		shoppingCart.setCreatedOn(date);
		shoppingCart.setCustomer(customer);
		shoppingCart.setProducts(products);
		
		model.addAttribute("date", new Date());
        
        
		model.addAttribute("customer", customer);
		model.addAttribute("products", products);
		model.addAttribute("shoppingCart", shoppingCart);
		return "new_shoppingCart";
	}
	
	@GetMapping("/showUpdatingShoppingCartForm/{id}")
	public String showUpdatingShoppingCartForm(@PathVariable("id")Integer id, Model model) {
		ShoppingCart cart = shopRepository.findById(id).get();
		model.addAttribute("cart",cart);
		return "update_shoppingCart";
		
	}
	
	
	@GetMapping("/deleteShoppingCart/{id}")
	public String deleteShoppingCart(@PathVariable ("id")Integer id, Product product) {
		
		shopRepository.deleteById(id);
		
		return "redirect:/shoppingCart";
	}
	
	@GetMapping("paga/{pagaNumbers}")
	public String findPagina(@PathVariable("pagaNumbers") Integer pagaNumbers,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		
		Integer pageSize = 2;
		
		Page<ShoppingCart>pages = shopServices.findPagina(pagaNumbers, pageSize, sortField, sortDir);
		
		List<ShoppingCart> listShoppingCarts = pages.getContent();
		model.addAttribute("currentPage",pagaNumbers);
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalItems", pages.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		
		model.addAttribute("listShoppingCarts", listShoppingCarts);
		return "all_shopping_carts";
		
	}
}
