package com.ms.candidat.signaturemicroservice.Repo;

import com.ms.candidat.signaturemicroservice.models.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignatureRepo extends JpaRepository<Signature, Long> {
    Optional<Signature> findByCustomerIdAndActiveTrue(Long customerId);
}
