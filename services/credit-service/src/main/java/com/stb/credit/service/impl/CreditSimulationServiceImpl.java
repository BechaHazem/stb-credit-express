package com.stb.credit.service.impl;


import org.springframework.stereotype.Service;

import com.stb.credit.dto.AmortizationLineDTO;
import com.stb.credit.dto.CreditSimulationRequest;
import com.stb.credit.dto.CreditSimulationResponse;
import com.stb.credit.service.CreditSimulationService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditSimulationServiceImpl implements CreditSimulationService {

 private static final BigDecimal ANNUAL_RATE = BigDecimal.valueOf(0.09); // 9% static
 private static final MathContext MC = new MathContext(12, RoundingMode.HALF_UP);

 @Override
 public CreditSimulationResponse simulate(CreditSimulationRequest request) {
     BigDecimal P = request.getLoanAmount();
     int n = request.getLoanTermMonths();
     int grace = request.getGracePeriodMonths();

     BigDecimal monthlyRate = ANNUAL_RATE.divide(BigDecimal.valueOf(12), MC);

     // annuity formula (excluding grace)
     int effectiveTerm = n - grace;
     BigDecimal monthlyPayment = P.multiply(monthlyRate, MC)
             .divide(BigDecimal.ONE.subtract((BigDecimal.ONE.add(monthlyRate, MC)).pow(-effectiveTerm, MC)), MC);

     List<AmortizationLineDTO> schedule = new ArrayList<>();
     BigDecimal balance = P;

     // Grace period (interest-only)
     for (int g = 1; g <= grace; g++) {
         BigDecimal interest = balance.multiply(monthlyRate, MC);
         BigDecimal payment = interest; // interest-only during grace
         schedule.add(new AmortizationLineDTO(g, balance, payment, interest, BigDecimal.ZERO, balance));
     }

     // Repayment after grace
     for (int k = grace + 1; k <= n; k++) {
         BigDecimal interest = balance.multiply(monthlyRate, MC);
         BigDecimal principal = monthlyPayment.subtract(interest, MC);
         balance = balance.subtract(principal, MC);

         if (k == n) { // last adjustment
             principal = principal.add(balance);
             monthlyPayment = principal.add(interest);
             balance = BigDecimal.ZERO;
         }

         schedule.add(new AmortizationLineDTO(k, balance.add(principal), monthlyPayment, interest, principal, balance));
     }

     // Totals
     BigDecimal totalInterest = schedule.stream()
             .map(AmortizationLineDTO::getInterest)
             .reduce(BigDecimal.ZERO, BigDecimal::add);
     BigDecimal totalCost = schedule.stream()
             .map(AmortizationLineDTO::getPayment)
             .reduce(BigDecimal.ZERO, BigDecimal::add);
     BigDecimal apr = ANNUAL_RATE; // placeholder for now (true APR = IRR calc)

     return new CreditSimulationResponse(
             P, n, grace,
             monthlyPayment.setScale(2, RoundingMode.HALF_UP),
             totalCost.setScale(2, RoundingMode.HALF_UP),
             totalInterest.setScale(2, RoundingMode.HALF_UP),
             apr.setScale(4, RoundingMode.HALF_UP),
             schedule
     );
 }
}
