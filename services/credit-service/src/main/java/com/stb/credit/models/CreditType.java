package com.stb.credit.models;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "credit_types")
public class CreditType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String type;

    @Column(precision = 5, scale = 4) // Example: 0.0900 for 9%
    private BigDecimal apr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }
}
