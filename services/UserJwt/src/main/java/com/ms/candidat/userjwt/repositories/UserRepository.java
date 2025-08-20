package com.ms.candidat.userjwt.repositories;

import com.ms.candidat.userjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
    boolean existsByClientNumber(Long clientNumber);


}
