package com.example.payments.service;

import com.example.payments.model.PaymentMethodType;

public class UnsupportedPaymentMethodException extends RuntimeException {
    public UnsupportedPaymentMethodException(PaymentMethodType t) { super("No processor for " + t); }
}
