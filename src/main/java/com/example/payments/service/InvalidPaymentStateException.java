package com.example.payments.service;

public class InvalidPaymentStateException extends RuntimeException {
    public InvalidPaymentStateException(String msg) { super(msg); }
}
