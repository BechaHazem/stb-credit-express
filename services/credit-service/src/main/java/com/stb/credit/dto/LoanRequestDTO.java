package com.stb.credit.dto;

public class LoanRequestDTO {

	private Long id;
    private CustomerDTO customer;
    private String creditType;
    private String accountNumber;
    private String loanPurpose;
    private Double loanAmount;
    private Integer loanDuration;
    private Integer gracePeriod;
    private String documents;
    private Boolean acceptTerms;
    private Integer step;
    private String libelle;
    private Long simulationId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CustomerDTO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}
	public String getCreditType() {
		return creditType;
	}
	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getLoanPurpose() {
		return loanPurpose;
	}
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Integer getLoanDuration() {
		return loanDuration;
	}
	public void setLoanDuration(Integer loanDuration) {
		this.loanDuration = loanDuration;
	}
	public Integer getGracePeriod() {
		return gracePeriod;
	}
	public void setGracePeriod(Integer gracePeriod) {
		this.gracePeriod = gracePeriod;
	}
	public String getDocuments() {
		return documents;
	}
	public void setDocuments(String documents) {
		this.documents = documents;
	}
	public Boolean getAcceptTerms() {
		return acceptTerms;
	}
	public void setAcceptTerms(Boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Long getSimulationId() {
		return simulationId;
	}
	public void setSimulationId(Long simulationId) {
		this.simulationId = simulationId;
	}
    
    
}
