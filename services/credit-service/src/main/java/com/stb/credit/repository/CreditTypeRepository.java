package com.stb.credit.repository;

import com.stb.credit.models.CreditType;
import com.stb.credit.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditTypeRepository extends JpaRepository<CreditType, Long> {
}
