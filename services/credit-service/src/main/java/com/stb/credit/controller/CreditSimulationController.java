package com.stb.credit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stb.credit.dto.CreditSimulationRequest;
import com.stb.credit.dto.CreditSimulationResponse;
import com.stb.credit.models.CreditSimulation;
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
 
 @PostMapping("/{customerId}")
 public CreditSimulation saveSimulation(
         @PathVariable Long customerId,
         @RequestBody CreditSimulationResponse dto
 ) {
     return simulationService.saveSimulation(customerId, dto);
 }
 @PutMapping("/{id}")
 public CreditSimulation updateSimulation(
         @PathVariable Long id,
         @RequestBody CreditSimulationResponse dto
 ) {
     return simulationService.updateSimulation(id, dto);
 }

 @GetMapping("/{customerId}")
 public java.util.List<CreditSimulation> getCustomerSimulations(@PathVariable Long customerId) {
     return simulationService.getSimulationsForCustomer(customerId);
 }
}
