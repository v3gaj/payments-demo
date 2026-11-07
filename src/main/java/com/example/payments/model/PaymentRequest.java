package com.example.payments.model;

import java.math.BigDecimal;
import java.util.Currency;

/** Entrada única del dominio. COMPONE un PaymentMethodDetails ("tiene un"):
 *  PaymentService solo lee method().type() y no necesita saber más. */
public record PaymentRequest(String idempotencyKey, BigDecimal amount,
                             Currency currency, PaymentMethodDetails method) {}
