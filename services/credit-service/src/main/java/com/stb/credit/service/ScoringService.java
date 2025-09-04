package com.stb.credit.service;

import java.io.IOException;
import java.util.Map;

import com.stb.credit.dto.LoanRequestDTO;


public interface ScoringService {

	Map<String, Object> calculateScore(LoanRequestDTO loan) throws IOException; 
}
