package com.stb.credit.service.impl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stb.credit.dto.CustomerDTO;
import com.stb.credit.models.Customer;
import com.stb.credit.repository.CustomerRepository;
import com.stb.credit.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
    private CustomerRepository customerRepository;
	
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findById(Long id) {
        Customer entity = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + id));
        return modelMapper.map(entity, CustomerDTO.class);
    }
}