package com.example.payments.processor;

import com.example.payments.model.PaymentResult;
import java.math.BigDecimal;

/** Capacidad OPCIONAL (ISP): evita métodos que lancen UnsupportedOperationException. */
public interface Refundable { PaymentResult refund(String paymentId, BigDecimal amount); }
