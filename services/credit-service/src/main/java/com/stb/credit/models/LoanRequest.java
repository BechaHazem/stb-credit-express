package com.stb.credit.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan_requests")
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String creditType;
    private String accountNumber;
    private String loanPurpose;
    private Double loanAmount;
    private Integer loanDuration;
    private Integer gracePeriod;

    @Column(length = 1000)
    private String documents; // comma-separated or JSON string

    private Boolean acceptTerms;
    private String agence;
    private Integer step;
    private String libelle;

    private Long simulationId; // optional

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
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

	public String getAgence() {
		return agence;
	}

	public void setAgence(String agence) {
		this.agence = agence;
	}
    
    
}
