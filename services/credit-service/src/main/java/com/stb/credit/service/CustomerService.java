package com.stb.credit.service;


import java.math.BigDecimal;

import com.stb.credit.dto.CustomerDTO;

public interface CustomerService {
	CustomerDTO findById(Long id);
    CustomerDTO updateScore(Long customerId, BigDecimal newScore);

}
