package com.example.payments.model;

/** Salida común: todos los Processors devuelven lo mismo, sea cual sea el método. */
public record PaymentResult(String paymentId, PaymentStatus status,
                            String providerRef, String failureReason) {}
