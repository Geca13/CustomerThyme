package com.example.custhy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.custhy.entity.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    Boolean existsByManufactorerName(String manufactorerName);
	
	Manufacturer findByManufactorerName(String manufactorerName);
	
}
