package com.example.payments.processor;

import com.example.payments.model.PaymentMethodType;
import com.example.payments.model.PaymentRequest;
import com.example.payments.model.PaymentResult;

/**
 * Contrato MÍNIMO de todo método de pago (la "Strategy").
 * - type(): lo usa ProcessorRegistry para indexar la implementación.
 * - authorize(): PaymentRequest -> PaymentResult.
 */
public interface PaymentProcessor {
    PaymentMethodType type();
    PaymentResult authorize(PaymentRequest req);
}
