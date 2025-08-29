package com.stb.credit.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String description;
    private BigDecimal rate;
    private BigDecimal minimumAmount;
    private BigDecimal maximumAmount;
    private Integer minimumTerm;
    private Integer maximumTerm;
    private Integer minimumAge;
    private Integer maximumAge;
    private Integer minimumDeferredPeriod;
    private Integer maximumDeferredPeriod;
    private BigDecimal issueFeeAmount1;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(BigDecimal minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public BigDecimal getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(BigDecimal maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public Integer getMinimumTerm() {
        return minimumTerm;
    }

    public void setMinimumTerm(Integer minimumTerm) {
        this.minimumTerm = minimumTerm;
    }

    public Integer getMaximumTerm() {
        return maximumTerm;
    }

    public void setMaximumTerm(Integer maximumTerm) {
        this.maximumTerm = maximumTerm;
    }

    public Integer getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Integer getMaximumAge() {
        return maximumAge;
    }

    public void setMaximumAge(Integer maximumAge) {
        this.maximumAge = maximumAge;
    }

    public Integer getMinimumDeferredPeriod() {
        return minimumDeferredPeriod;
    }

    public void setMinimumDeferredPeriod(Integer minimumDeferredPeriod) {
        this.minimumDeferredPeriod = minimumDeferredPeriod;
    }

    public Integer getMaximumDeferredPeriod() {
        return maximumDeferredPeriod;
    }

    public void setMaximumDeferredPeriod(Integer maximumDeferredPeriod) {
        this.maximumDeferredPeriod = maximumDeferredPeriod;
    }

    public BigDecimal getIssueFeeAmount1() {
        return issueFeeAmount1;
    }

    public void setIssueFeeAmount1(BigDecimal issueFeeAmount1) {
        this.issueFeeAmount1 = issueFeeAmount1;
    }



}
