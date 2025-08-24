package com.stb.credit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stb.credit.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}