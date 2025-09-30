package com.stb.credit.service;

import java.io.IOException;
import java.util.List;

import com.stb.credit.dto.LoanRequestDTO;

public interface LoanRequestService {
    LoanRequestDTO createLoanRequest(LoanRequestDTO dto);
    List<LoanRequestDTO> getAllLoanRequests();
    LoanRequestDTO getLoanRequestById(Long id);
    List<LoanRequestDTO> getLoanRequestsByCustomerId(Long customerId);
    List<LoanRequestDTO> getLoanRequestsByAgence(String agence);
    LoanRequestDTO updateLoanRequest(LoanRequestDTO dto);
    public void attachSignatureToLoanRequest(Long loanRequestId, String signatureUrl) throws IOException;
    public void attachSignatureToFinalContract(Long loanRequestId, String signatureUrl) throws IOException;
}