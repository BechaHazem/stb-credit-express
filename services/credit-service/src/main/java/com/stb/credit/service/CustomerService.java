package com.stb.credit.service;


import com.stb.credit.dto.CustomerDTO;

public interface CustomerService {
	CustomerDTO findById(Long id);
}
