package com.example.payments.model;

/** Ciclo de vida: PENDING/AUTHORIZED -> CAPTURED -> REFUNDED (o FAILED). */
public enum PaymentStatus { PENDING, AUTHORIZED, CAPTURED, REFUNDED, FAILED }
