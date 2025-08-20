package com.stb.credit.dto;



import java.math.BigDecimal;

public class AmortizationLineDTO {
 private int period;
 private BigDecimal openingBalance;
 private BigDecimal payment;
 private BigDecimal interest;
 private BigDecimal principal;
 private BigDecimal closingBalance;

 // constructor
 public AmortizationLineDTO(int period, BigDecimal openingBalance, BigDecimal payment,
                         BigDecimal interest, BigDecimal principal, BigDecimal closingBalance) {
     this.period = period;
     this.openingBalance = openingBalance;
     this.payment = payment;
     this.interest = interest;
     this.principal = principal;
     this.closingBalance = closingBalance;
 }

 // getters
 public int getPeriod() { return period; }
 public BigDecimal getOpeningBalance() { return openingBalance; }
 public BigDecimal getPayment() { return payment; }
 public BigDecimal getInterest() { return interest; }
 public BigDecimal getPrincipal() { return principal; }
 public BigDecimal getClosingBalance() { return closingBalance; }

public void setPeriod(int period) {
	this.period = period;
}

public void setOpeningBalance(BigDecimal openingBalance) {
	this.openingBalance = openingBalance;
}

public void setPayment(BigDecimal payment) {
	this.payment = payment;
}

public void setInterest(BigDecimal interest) {
	this.interest = interest;
}

public void setPrincipal(BigDecimal principal) {
	this.principal = principal;
}

public void setClosingBalance(BigDecimal closingBalance) {
	this.closingBalance = closingBalance;
}
 
 
}
