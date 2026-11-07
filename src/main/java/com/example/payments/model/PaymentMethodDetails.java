package com.example.payments.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Jerarquía CERRADA (sealed): cada variante lleva solo los datos de su método.
 * - Relación 1:1 con PaymentMethodType (type()).
 * - Jackson usa el campo "type" del JSON para elegir la variante al deserializar.
 * - Los permitidos deben estar en el MISMO paquete (módulo sin nombre).
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @Type(value = CardDetails.class,         name = "CARD"),
    @Type(value = PayPalDetails.class,       name = "PAYPAL"),
    @Type(value = SinpeDetails.class,        name = "SINPE_MOVIL"),
    @Type(value = BankTransferDetails.class, name = "BANK_TRANSFER")
})
public sealed interface PaymentMethodDetails
        permits CardDetails, PayPalDetails, SinpeDetails, BankTransferDetails {
    PaymentMethodType type();
}
