package com.stb.credit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stb.credit.dto.LoanRequestDTO;
import com.stb.credit.service.LoanRequestService;

@RestController
@RequestMapping("/api/loan-requests")
public class LoanRequestController {

	@Autowired
    private  LoanRequestService loanRequestService;
	

    @PostMapping
    public ResponseEntity<LoanRequestDTO> createLoanRequest(@RequestBody LoanRequestDTO dto) {
        LoanRequestDTO saved = loanRequestService.createLoanRequest(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<LoanRequestDTO>> getAllLoanRequests() {
        return ResponseEntity.ok(loanRequestService.getAllLoanRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanRequestDTO> getLoanRequest(@PathVariable Long id) {
        return ResponseEntity.ok(loanRequestService.getLoanRequestById(id));
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanRequestDTO>> getLoanRequestsByCustomer(@PathVariable Long customerId) {
        List<LoanRequestDTO> requests = loanRequestService.getLoanRequestsByCustomerId(customerId);
        return ResponseEntity.ok(requests);
    }
    
//    @GetMapping("/{id}/pdf")
//    public ResponseEntity<byte[]> getLoanPdf(@PathVariable Long id) {
//        LoanRequestDTO loanRequest = loanRequestService.getLoanRequestById(id);
//        byte[] pdfBytes = pdfReportService.generateLoanRequestPdf(loanRequest);
//
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "inline; filename=loan_request_" + id + ".pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(pdfBytes);
//    }
}
