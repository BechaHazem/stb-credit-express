package com.ms.candidat.userjwt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.candidat.userjwt.models.User;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
    boolean existsByClientNumber(Long clientNumber);
    List<User> findByAgence(String agence);


}
