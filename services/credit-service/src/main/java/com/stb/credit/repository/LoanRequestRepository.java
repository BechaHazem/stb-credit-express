package com.stb.credit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stb.credit.models.LoanRequest;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
	List<LoanRequest> findByCustomerId(Long customerId);
	List<LoanRequest> findByAgence(String agence);

}