package com.stb.credit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stb.credit.dto.CreditSimulationRequest;
import com.stb.credit.dto.CreditSimulationResponse;
import com.stb.credit.service.CreditSimulationService;

@RestController
@RequestMapping("/api")
public class CreditSimulationController {

 private final CreditSimulationService simulationService;

 @Autowired
 public CreditSimulationController(CreditSimulationService simulationService) {
     this.simulationService = simulationService;
 }

 @PostMapping("/simulator")
 public CreditSimulationResponse simulate(@RequestBody CreditSimulationRequest request) {
     return simulationService.simulate(request);
 }
}
