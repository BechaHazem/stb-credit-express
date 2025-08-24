package com.stb.credit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stb.credit.models.CreditSimulation;

public interface CreditSimulationRepository extends JpaRepository<CreditSimulation, Long> {
    List<CreditSimulation> findByCustomerId(Long customerId);
    List<CreditSimulation> findByCustomerIdAndEnabledTrue(Long customerId);
}