package com.example.payments.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SinpeDetails(@NotBlank @Pattern(regexp = "\\d{8}") String phone) implements PaymentMethodDetails {
    public PaymentMethodType type() { return PaymentMethodType.SINPE_MOVIL; }
}
