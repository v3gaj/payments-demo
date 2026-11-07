package com.example.payments.processor;

import com.example.payments.model.PaymentResult;
import java.math.BigDecimal;

/** Capacidad OPCIONAL (ISP): solo la implementan los métodos con autorización + captura diferida. */
public interface Capturable { PaymentResult capture(String paymentId, BigDecimal amount); }
