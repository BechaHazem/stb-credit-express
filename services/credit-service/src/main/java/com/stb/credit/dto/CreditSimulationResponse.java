package com.stb.credit.dto;

import java.math.BigDecimal;
import java.util.List;

public class CreditSimulationResponse {
    private Long id;
 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

private BigDecimal loanAmount;
 private int loanTermMonths;
 private int gracePeriodMonths;
 private BigDecimal monthlyPayment;
 private BigDecimal totalCost;
 private BigDecimal totalInterest;
 private BigDecimal apr;
 private List<AmortizationLineDTO> schedule;

 // constructor
 public CreditSimulationResponse(BigDecimal loanAmount, int loanTermMonths, int gracePeriodMonths,
                                 BigDecimal monthlyPayment, BigDecimal totalCost,
                                 BigDecimal totalInterest, BigDecimal apr,
                                 List<AmortizationLineDTO> schedule) {
     this.loanAmount = loanAmount;
     this.loanTermMonths = loanTermMonths;
     this.gracePeriodMonths = gracePeriodMonths;
     this.monthlyPayment = monthlyPayment;
     this.totalCost = totalCost;
     this.totalInterest = totalInterest;
     this.apr = apr;
     this.schedule = schedule;
 }

 // getters
 public BigDecimal getLoanAmount() { return loanAmount; }
 public int getLoanTermMonths() { return loanTermMonths; }
 public int getGracePeriodMonths() { return gracePeriodMonths; }
 public BigDecimal getMonthlyPayment() { return monthlyPayment; }
 public BigDecimal getTotalCost() { return totalCost; }
 public BigDecimal getTotalInterest() { return totalInterest; }
 public BigDecimal getApr() { return apr; }
 public List<AmortizationLineDTO> getSchedule() { return schedule; }

public void setLoanAmount(BigDecimal loanAmount) {
	this.loanAmount = loanAmount;
}

public void setLoanTermMonths(int loanTermMonths) {
	this.loanTermMonths = loanTermMonths;
}

public void setGracePeriodMonths(int gracePeriodMonths) {
	this.gracePeriodMonths = gracePeriodMonths;
}

public void setMonthlyPayment(BigDecimal monthlyPayment) {
	this.monthlyPayment = monthlyPayment;
}

public void setTotalCost(BigDecimal totalCost) {
	this.totalCost = totalCost;
}

public void setTotalInterest(BigDecimal totalInterest) {
	this.totalInterest = totalInterest;
}

public void setApr(BigDecimal apr) {
	this.apr = apr;
}

public void setSchedule(List<AmortizationLineDTO> schedule) {
	this.schedule = schedule;
}
 
 
}

