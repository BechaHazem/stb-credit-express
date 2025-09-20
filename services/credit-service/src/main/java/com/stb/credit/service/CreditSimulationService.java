package com.stb.credit.service;

import java.util.List;

import com.stb.credit.dto.CreditSimulationRequest;
import com.stb.credit.dto.CreditSimulationResponse;
import com.stb.credit.models.CreditSimulation;

public interface CreditSimulationService {
    CreditSimulationResponse simulate(CreditSimulationRequest request);
    CreditSimulation saveSimulation(Long customerId, CreditSimulationResponse dto);
    CreditSimulation updateSimulation(Long id, CreditSimulationResponse dto);
    List<CreditSimulation> getSimulationsForCustomer(Long customerId);
    CreditSimulation getSimulationById(Long id);
}