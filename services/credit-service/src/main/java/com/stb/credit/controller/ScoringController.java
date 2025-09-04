package com.stb.credit.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stb.credit.dto.LoanRequestDTO;
import com.stb.credit.service.ScoringService;

@RestController
@RequestMapping("/api/scoring")
public class ScoringController {

	@Autowired
	private ScoringService scoringService;
	
	@PostMapping("/calculate_customer_score")
	public Map<String, Object> calculateScore(@RequestBody LoanRequestDTO loan) throws IOException {

		return scoringService.calculateScore(loan);

	}
	
}
