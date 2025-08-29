package com.stb.credit.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank String code,
        @NotBlank String description,
        @DecimalMin("0.01") BigDecimal rate,
        @DecimalMin("0.01") BigDecimal minimumAmount,
        @DecimalMin("0.01") BigDecimal maximumAmount,
        @Min(1) Integer minimumTerm,
        @Min(1) Integer maximumTerm,
        @Min(1) Integer minimumAge,
        @Min(1) Integer maximumAge,
        @Min(0) Integer minimumDeferredPeriod,
        @Min(0) Integer maximumDeferredPeriod,
        @DecimalMin("0") BigDecimal issueFeeAmount1

) {
}
