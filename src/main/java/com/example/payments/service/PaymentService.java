package com.example.payments.service;

import com.example.payments.model.*;
import com.example.payments.persistence.Payment;
import com.example.payments.persistence.PaymentRepository;
import com.example.payments.processor.*;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Orquestador. NO conoce métodos concretos.
 * Depende de: ProcessorRegistry (elige Strategy), PaymentRepository (persiste),
 * IdempotencyStore (evita duplicados). Lo invoca PaymentController.
 */
@Service
public class PaymentService {
    private final ProcessorRegistry registry;
    private final PaymentRepository repo;
    private final IdempotencyStore idem;

    public PaymentService(ProcessorRegistry registry, PaymentRepository repo, IdempotencyStore idem) {
        this.registry = registry; this.repo = repo; this.idem = idem;
    }

    @Transactional
    public PaymentResult pay(PaymentRequest req) {
        return idem.execute(req.idempotencyKey(), () -> {
            // request -> type -> Registry -> Processor concreto -> authorize
            var result = registry.resolve(req.method().type()).authorize(req);
            repo.save(Payment.from(req, result));
            return result;
        });
    }

    @Transactional
    public PaymentResult capture(String id) {
        var p = find(id);
        // Primero capacidad (422), luego estado (409)
        if (!(registry.resolve(p.methodType()) instanceof Capturable c))
            throw new OperationNotSupportedException(p.methodType(), "capture");
        if (p.status() != PaymentStatus.AUTHORIZED)
            throw new InvalidPaymentStateException("Only AUTHORIZED payments can be captured (was " + p.status() + ")");
        var result = c.capture(id, p.amount());
        p.status(result.status());
        return result;
    }

    @Transactional
    public PaymentResult refund(String id, BigDecimal amount) {
        var p = find(id);
        if (!(registry.resolve(p.methodType()) instanceof Refundable r))
            throw new OperationNotSupportedException(p.methodType(), "refund");
        if (p.status() != PaymentStatus.CAPTURED)
            throw new InvalidPaymentStateException("Only CAPTURED payments can be refunded (was " + p.status() + ")");
        var result = r.refund(id, amount != null ? amount : p.amount());
        p.status(result.status());
        return result;
    }

    private Payment find(String id) {
        return repo.findById(id).orElseThrow(() -> new PaymentNotFoundException(id));
    }
}
