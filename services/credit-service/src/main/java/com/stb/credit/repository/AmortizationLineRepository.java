package com.stb.credit.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.stb.credit.models.AmortizationLine;

public interface AmortizationLineRepository extends JpaRepository<AmortizationLine, Long> {
}
