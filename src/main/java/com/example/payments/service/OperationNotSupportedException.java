package com.example.payments.service;

import com.example.payments.model.PaymentMethodType;

public class OperationNotSupportedException extends RuntimeException {
    public OperationNotSupportedException(PaymentMethodType t, String op) {
        super(t + " does not support " + op);
    }
}
