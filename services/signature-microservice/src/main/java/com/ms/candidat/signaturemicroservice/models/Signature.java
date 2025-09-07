package com.ms.candidat.signaturemicroservice.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "signatures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Signature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;   // comes from userjwt service

    private String signatureUrl; // URL of uploaded signature (Cloudinary / local)

    private boolean active;     // true = current signature, false = old
}
