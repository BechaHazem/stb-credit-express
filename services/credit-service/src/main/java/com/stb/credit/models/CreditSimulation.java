package com.stb.credit.models;


import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class CreditSimulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private BigDecimal loanAmount;
    private int loanTermMonths;
    private int gracePeriodMonths;
    private BigDecimal monthlyPayment;
    private BigDecimal totalCost;
    private BigDecimal totalInterest;
    private BigDecimal apr;
	private boolean enabled = true;

    @OneToMany(mappedBy = "simulation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AmortizationLine> schedule;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public int getLoanTermMonths() {
		return loanTermMonths;
	}

	public void setLoanTermMonths(int loanTermMonths) {
		this.loanTermMonths = loanTermMonths;
	}

	public int getGracePeriodMonths() {
		return gracePeriodMonths;
	}

	public void setGracePeriodMonths(int gracePeriodMonths) {
		this.gracePeriodMonths = gracePeriodMonths;
	}

	public BigDecimal getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(BigDecimal monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(BigDecimal totalInterest) {
		this.totalInterest = totalInterest;
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}

	public List<AmortizationLine> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<AmortizationLine> schedule) {
		this.schedule = schedule;
	}
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
