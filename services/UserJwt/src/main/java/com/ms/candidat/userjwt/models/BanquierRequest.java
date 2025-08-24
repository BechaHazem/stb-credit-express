package com.ms.candidat.userjwt.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BanquierRequest(
        @NotBlank String firstname,
        @NotBlank String lastname,
        @Email   String email,
        @Size(min = 6) String password,
        @NotBlank String phone,
        @NotBlank String agence
) {}
