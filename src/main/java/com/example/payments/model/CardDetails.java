package com.example.payments.model;

import jakarta.validation.constraints.NotBlank;

/** Tarjeta: solo TOKEN (nunca el PAN) -> alcance PCI mínimo. */
public record CardDetails(@NotBlank String token, String brand) implements PaymentMethodDetails {
    public PaymentMethodType type() { return PaymentMethodType.CARD; }
}
