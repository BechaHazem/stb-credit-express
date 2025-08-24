package com.stb.credit.service;

import java.util.List;

import com.stb.credit.dto.LoanRequestDTO;

public interface LoanRequestService {
    LoanRequestDTO createLoanRequest(LoanRequestDTO dto);
    List<LoanRequestDTO> getAllLoanRequests();
    LoanRequestDTO getLoanRequestById(Long id);
    List<LoanRequestDTO> getLoanRequestsByCustomerId(Long customerId);
}