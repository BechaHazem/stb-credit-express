package com.stb.credit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stb.credit.models.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
	
    List<Document> findByLoanRequestIdAndCustomerId(Long loanRequestId, Long customerId);
}
