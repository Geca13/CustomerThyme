package com.example.custhy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.custhy.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
