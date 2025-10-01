package com.stb.credit.service.impl;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stb.credit.dto.AmortizationLineDTO;
import com.stb.credit.dto.CreditSimulationRequest;
import com.stb.credit.dto.CreditSimulationResponse;
import com.stb.credit.models.AmortizationLine;
import com.stb.credit.models.CreditSimulation;
import com.stb.credit.models.CreditType;
import com.stb.credit.repository.CreditSimulationRepository;
import com.stb.credit.repository.CreditTypeRepository;
import com.stb.credit.service.CreditSimulationService;

@Service
public class CreditSimulationServiceImpl implements CreditSimulationService {

    private static final MathContext MC = new MathContext(12, RoundingMode.HALF_UP);

    @Autowired
    private CreditSimulationRepository repository;

    @Autowired
    private CreditTypeRepository creditTypeRepository;

    @Override
    public CreditSimulationResponse simulate(CreditSimulationRequest request) {
        CreditType creditType = creditTypeRepository.findById(request.getCreditTypeId())
                .orElseThrow(() -> new RuntimeException("Credit type not found"));


        BigDecimal annualRate = creditType.getApr();
        BigDecimal P = request.getLoanAmount();
        int n = request.getLoanTermMonths();
        int grace = request.getGracePeriodMonths();


        if (annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid APR value: " + annualRate + " for credit type: " + creditType.getType());
        }
        if (P.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Loan amount must be positive");
        }
        if (n <= 0) {
            throw new RuntimeException("Loan term must be positive");
        }
        if (grace < 0 || grace >= n) {
            throw new RuntimeException("Grace period must be between 0 and " + (n-1));
        }


        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), MC);


        int effectiveTerm = n - grace;


        if (effectiveTerm <= 0) {
            throw new RuntimeException("Loan term must be greater than grace period");
        }


        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate, MC);
        BigDecimal pow = onePlusRate.pow(-effectiveTerm, MC);
        BigDecimal denominator = BigDecimal.ONE.subtract(pow, MC);


        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("Calculation error: denominator is zero");
        }


        BigDecimal monthlyPayment = P.multiply(monthlyRate, MC).divide(denominator, MC);

        List<AmortizationLineDTO> schedule = new ArrayList<>();
        BigDecimal balance = P;


        for (int g = 1; g <= grace; g++) {
            BigDecimal interest = balance.multiply(monthlyRate, MC);
            BigDecimal payment = interest; // interest-only during grace
            schedule.add(new AmortizationLineDTO(g, balance, payment, interest, BigDecimal.ZERO, balance));
        }


        for (int k = grace + 1; k <= n; k++) {
            BigDecimal interest = balance.multiply(monthlyRate, MC);
            BigDecimal principal = monthlyPayment.subtract(interest, MC);
            balance = balance.subtract(principal, MC);

            if (k == n) { 
                principal = principal.add(balance);
                monthlyPayment = principal.add(interest);
                balance = BigDecimal.ZERO;
            }

            schedule.add(new AmortizationLineDTO(k, balance.add(principal), monthlyPayment, interest, principal, balance));
        }

        BigDecimal totalInterest = schedule.stream()
                .map(AmortizationLineDTO::getInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCost = schedule.stream()
                .map(AmortizationLineDTO::getPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal apr = annualRate;

        return new CreditSimulationResponse(
                P, n, grace,
                monthlyPayment.setScale(2, RoundingMode.HALF_UP),
                totalCost.setScale(2, RoundingMode.HALF_UP),
                totalInterest.setScale(2, RoundingMode.HALF_UP),
                apr.setScale(4, RoundingMode.HALF_UP),
                schedule,
                creditType.getId(),
                creditType.getType()
        );
    } 
    @Override
 public CreditSimulation saveSimulation(Long customerId, CreditSimulationResponse dto) {
     CreditSimulation sim = mapToEntity(dto);
     sim.setCustomerId(customerId);
     return repository.save(sim);
 }
 @Override
 public CreditSimulation updateSimulation(Long id, CreditSimulationResponse dto) {
     CreditSimulation existing = repository.findById(id)
             .orElseThrow(() -> new RuntimeException("Simulation not found"));
     existing.setLoanAmount(dto.getLoanAmount());
     existing.setLoanTermMonths(dto.getLoanTermMonths());
     existing.setGracePeriodMonths(dto.getGracePeriodMonths());
     existing.setMonthlyPayment(dto.getMonthlyPayment());
     existing.setTotalCost(dto.getTotalCost());
     existing.setTotalInterest(dto.getTotalInterest());
     existing.setApr(dto.getApr());

     existing.getSchedule().clear();
     existing.getSchedule().addAll(
             dto.getSchedule().stream().map(line -> {
                 AmortizationLine l = new AmortizationLine();
                 l.setPeriod(line.getPeriod());
                 l.setOpeningBalance(line.getOpeningBalance());
                 l.setPayment(line.getPayment());
                 l.setInterest(line.getInterest());
                 l.setPrincipal(line.getPrincipal());
                 l.setClosingBalance(line.getClosingBalance());
                 l.setSimulation(existing);
                 return l;
             }).collect(Collectors.toList())
     );

     return repository.save(existing);
 }

 @Override
 public List<CreditSimulation> getSimulationsForCustomer(Long customerId) {
	    return repository.findByCustomerIdAndEnabledTrue(customerId);
 }

 private CreditSimulation mapToEntity(CreditSimulationResponse dto) {
     CreditSimulation sim = new CreditSimulation();
     sim.setLoanAmount(dto.getLoanAmount());
     sim.setId(dto.getId());
     sim.setLoanTermMonths(dto.getLoanTermMonths());
     sim.setGracePeriodMonths(dto.getGracePeriodMonths());
     sim.setMonthlyPayment(dto.getMonthlyPayment());
     sim.setTotalCost(dto.getTotalCost());
     sim.setTotalInterest(dto.getTotalInterest());
     sim.setApr(dto.getApr());
     CreditType creditType = creditTypeRepository
             .findById(dto.getCreditTypeId())
             .orElseThrow(() -> new RuntimeException("CreditType not found"));
     sim.setCreditType(creditType);       

     sim.setSchedule(
             dto.getSchedule().stream().map(line -> {
                 AmortizationLine l = new AmortizationLine();
                 l.setPeriod(line.getPeriod());
                 l.setOpeningBalance(line.getOpeningBalance());
                 l.setPayment(line.getPayment());
                 l.setInterest(line.getInterest());
                 l.setPrincipal(line.getPrincipal());
                 l.setClosingBalance(line.getClosingBalance());
                 l.setSimulation(sim);
                 return l;
             }).collect(Collectors.toList())
     );
     return sim;
 }
 
 @Override
 public CreditSimulation getSimulationById(Long id) {
     return repository.findById(id)
             .orElseThrow(() -> new RuntimeException("Simulation not found with id: " + id));
 }
}
