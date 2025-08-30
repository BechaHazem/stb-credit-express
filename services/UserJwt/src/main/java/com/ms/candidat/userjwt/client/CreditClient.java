package com.ms.candidat.userjwt.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ms.candidat.userjwt.dtos.CustomerDTO;


	@FeignClient(name = "credit-service", url = "http://localhost:8094") 
	public interface CreditClient {

	    @GetMapping("/api/customers/{id}")
	    CustomerDTO getCustomer(@PathVariable("id") Long id);
	}

