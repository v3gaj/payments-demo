package com.example.payments.model;

import jakarta.validation.constraints.NotBlank;

public record BankTransferDetails(@NotBlank String iban, String bankCode) implements PaymentMethodDetails {
    public PaymentMethodType type() { return PaymentMethodType.BANK_TRANSFER; }
}
