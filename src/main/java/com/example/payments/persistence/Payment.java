package com.example.payments.persistence;

import com.example.payments.model.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

/** Entidad: guarda request + result. Se mapea con Payment.from(req, result). */
@Entity
@Table(name = "payments")
public class Payment {
    @Id private String id;
    @Enumerated(EnumType.STRING) private PaymentMethodType methodType;
    private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING) private PaymentStatus status;
    private String providerRef;
    private Instant createdAt = Instant.now();

    protected Payment() {}

    public static Payment from(PaymentRequest req, PaymentResult res) {
        var p = new Payment();
        p.id = res.paymentId();
        p.methodType = req.method().type();
        p.amount = req.amount();
        p.currency = req.currency().getCurrencyCode();
        p.status = res.status();
        p.providerRef = res.providerRef();
        return p;
    }

    public String id() { return id; }
    public PaymentMethodType methodType() { return methodType; }
    public BigDecimal amount() { return amount; }
    public PaymentStatus status() { return status; }
    public void status(PaymentStatus s) { this.status = s; }
}
