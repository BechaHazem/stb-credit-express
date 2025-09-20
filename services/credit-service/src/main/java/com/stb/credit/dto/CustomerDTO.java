package com.stb.credit.dto;

import java.math.BigDecimal;

public class CustomerDTO {

	
    private Long id;
    private String fullName;
    private String email;
    private String idType;
    private String idNumber;
    private String idIssueDate;
    private String fiscalNumber;
    private String employer;
    private String profession;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String accountNumber;
    private Integer age;
    private BigDecimal monthlyExpenses;
    private BigDecimal monthlyIncome;
    // Spouse info
    private String spouseName;
    private String spouseEmail;
    private String spouseIdType;
    private String spouseIdNumber;
    private String spouseIdIssueDate;
    private String spouseFiscalNumber;
    private String spouseEmployer;
    private String spouseProfession;
    private String spouseAddress;
    private String spouseCity;
    private String spousePostalCode;
    private String spousePhone;
    
    private BigDecimal score;
    
    
    
    
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getIdIssueDate() {
		return idIssueDate;
	}
	public void setIdIssueDate(String idIssueDate) {
		this.idIssueDate = idIssueDate;
	}
	public String getFiscalNumber() {
		return fiscalNumber;
	}
	public void setFiscalNumber(String fiscalNumber) {
		this.fiscalNumber = fiscalNumber;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSpouseName() {
		return spouseName;
	}
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	public String getSpouseEmail() {
		return spouseEmail;
	}
	public void setSpouseEmail(String spouseEmail) {
		this.spouseEmail = spouseEmail;
	}
	public String getSpouseIdType() {
		return spouseIdType;
	}
	public void setSpouseIdType(String spouseIdType) {
		this.spouseIdType = spouseIdType;
	}
	public String getSpouseIdNumber() {
		return spouseIdNumber;
	}
	public void setSpouseIdNumber(String spouseIdNumber) {
		this.spouseIdNumber = spouseIdNumber;
	}
	public String getSpouseIdIssueDate() {
		return spouseIdIssueDate;
	}
	public void setSpouseIdIssueDate(String spouseIdIssueDate) {
		this.spouseIdIssueDate = spouseIdIssueDate;
	}
	public String getSpouseFiscalNumber() {
		return spouseFiscalNumber;
	}
	public void setSpouseFiscalNumber(String spouseFiscalNumber) {
		this.spouseFiscalNumber = spouseFiscalNumber;
	}
	public String getSpouseEmployer() {
		return spouseEmployer;
	}
	public void setSpouseEmployer(String spouseEmployer) {
		this.spouseEmployer = spouseEmployer;
	}
	public String getSpouseProfession() {
		return spouseProfession;
	}
	public void setSpouseProfession(String spouseProfession) {
		this.spouseProfession = spouseProfession;
	}
	public String getSpouseAddress() {
		return spouseAddress;
	}
	public void setSpouseAddress(String spouseAddress) {
		this.spouseAddress = spouseAddress;
	}
	public String getSpouseCity() {
		return spouseCity;
	}
	public void setSpouseCity(String spouseCity) {
		this.spouseCity = spouseCity;
	}
	public String getSpousePostalCode() {
		return spousePostalCode;
	}
	public void setSpousePostalCode(String spousePostalCode) {
		this.spousePostalCode = spousePostalCode;
	}
	public String getSpousePhone() {
		return spousePhone;
	}
	public void setSpousePhone(String spousePhone) {
		this.spousePhone = spousePhone;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public BigDecimal getMonthlyExpenses() {
		return monthlyExpenses;
	}
	public void setMonthlyExpenses(BigDecimal monthlyExpenses) {
		this.monthlyExpenses = monthlyExpenses;
	}
	public BigDecimal getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(BigDecimal monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
    
    
}
