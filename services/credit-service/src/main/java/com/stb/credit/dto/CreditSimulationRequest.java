package com.stb.credit.dto;
import java.math.BigDecimal;

public class CreditSimulationRequest {

    private BigDecimal loanAmount;
    
    private int  loanTermMonths; 
    
    private int gracePeriodMonths;
	private Long creditTypeId;

	public Long getCreditTypeId() {
		return creditTypeId;
	}

	public void setCreditTypeId(Long creditTypeId) {
		this.creditTypeId = creditTypeId;
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

    
}
