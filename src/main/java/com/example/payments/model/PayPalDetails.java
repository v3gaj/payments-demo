package com.example.payments.model;

import jakarta.validation.constraints.NotBlank;

public record PayPalDetails(@NotBlank String orderId, String payerEmail) implements PaymentMethodDetails {
    public PaymentMethodType type() { return PaymentMethodType.PAYPAL; }
}
