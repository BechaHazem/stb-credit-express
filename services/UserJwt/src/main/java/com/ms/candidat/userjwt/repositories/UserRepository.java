package com.ms.candidat.userjwt.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.candidat.userjwt.models.Role;
import com.ms.candidat.userjwt.models.User;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);
    boolean existsByClientNumber(Long clientNumber);
    List<User> findByAgence(String agence);
    List<User> findByAgenceAndRole(String agence, Role role);

}
