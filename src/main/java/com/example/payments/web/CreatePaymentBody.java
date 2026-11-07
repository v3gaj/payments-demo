package com.example.payments.web;

import com.example.payments.model.PaymentMethodDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Currency;

/** DTO HTTP. "method" es polimórfico: el campo "type" decide la variante. */
public record CreatePaymentBody(@NotNull @Positive BigDecimal amount,
                                @NotNull Currency currency,
                                @NotNull @Valid PaymentMethodDetails method) {}
