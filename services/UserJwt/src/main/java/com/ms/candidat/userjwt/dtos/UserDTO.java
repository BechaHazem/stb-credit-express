package com.ms.candidat.userjwt.dtos;

import com.ms.candidat.userjwt.models.Role;

public class UserDTO {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private Long clientNumber;
    private Role role;
    private String agence;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAgence() {
		return agence;
	}
	public void setAgence(String agence) {
		this.agence = agence;
	}
	private CustomerDTO customer;

    public Long getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	// Getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
	public CustomerDTO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}
    
}
