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

import com.example.custhy.entity.Customer;
import com.example.custhy.repository.CustomerRepository;
import com.example.custhy.services.CustomerServices;






@Controller
public class CustomerController {
	
	@Autowired
	CustomerServices customerServices;
	
	
	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping("/")
	public String index(Model model) {
		
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		
	    return "index";
	}
	
	
	@GetMapping("/customer")
	public String viewHomePage(Model model) {
		
		 findPage(1,"firstName", "asc", model);
		 
		 return "all_customers";
		
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute ("customer") Customer customer) {
		
		customerRepository.save(customer);
		
		return "redirect:/customer";
	}
	
	@GetMapping("/showNewCustomerForm")
	public String showNewCustomerForm (Model model) {
		
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		
		return "new_customer";
	}
	
	@GetMapping("/showUpdateForm/{id}")
	public String showUpdateForm(@PathVariable("id")Integer id, Model model) {
		Customer customer = customerRepository.findById(id).get();
		model.addAttribute("customer",customer);
		return "update_customer";
		
	}
	
	@GetMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable ("id")Integer id, Customer customer) {
		
		customerRepository.deleteById(id);
		
		return "redirect:/customer";
	}
	
	@GetMapping("page/{pageNumber}")
	public String findPage(@PathVariable("pageNumber") Integer pageNumber,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		
		Integer pageSize = 2;
		
		Page<Customer>pages = customerServices.findPaginated(pageNumber, pageSize, sortField, sortDir);
		
		List<Customer> listCustomers = pages.getContent();
		model.addAttribute("currentPage",pageNumber);
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalItems", pages.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		
		model.addAttribute("listCustomers", listCustomers);
		return "all_customers";
		
	}

}
