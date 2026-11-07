package com.example.payments.provider;

import com.example.payments.model.PaymentResult;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * PROVEEDOR (Stripe, Tilopay...): quién procesa. Distinto de MÉTODO (qué es).
 * CardProcessor depende de esta interfaz; cambiar de PSP = otra implementación.
 */
public interface PaymentProvider {
    PaymentResult authorize(String token, BigDecimal amount, Currency currency);
    PaymentResult capture(String paymentId, BigDecimal amount);
    PaymentResult refund(String paymentId, BigDecimal amount);
}
