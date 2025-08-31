package com.stb.credit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stb.credit.dto.LoanRequestDTO;
import com.stb.credit.service.LoanRequestService;

import jakarta.validation.Valid;

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
    
    @GetMapping("/banker/{agence}")
    public ResponseEntity<List<LoanRequestDTO>> getLoanRequestsByAgence(@PathVariable String agence) {
        List<LoanRequestDTO> requests = loanRequestService.getLoanRequestsByAgence(agence);
        return ResponseEntity.ok(requests);
    }
    @PutMapping("/{id}")
    public ResponseEntity<LoanRequestDTO> updateLoanRequest(
            @PathVariable Long id,
            @Valid @RequestBody LoanRequestDTO dto) {

        if (!id.equals(dto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID mismatch");
        }
        LoanRequestDTO updated = loanRequestService.updateLoanRequest(dto);
        return ResponseEntity.ok(updated);
    }
    

}
