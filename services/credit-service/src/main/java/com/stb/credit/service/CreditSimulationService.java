package com.stb.credit.service;

import com.stb.credit.dto.CreditSimulationRequest;
import com.stb.credit.dto.CreditSimulationResponse;

public interface CreditSimulationService {
    CreditSimulationResponse simulate(CreditSimulationRequest request);
}