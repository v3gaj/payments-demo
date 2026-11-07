package com.example.payments.web;

import com.example.payments.model.PaymentRequest;
import com.example.payments.model.PaymentResult;
import com.example.payments.service.PaymentService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.*;

/** Capa HTTP: traduce JSON <-> dominio y delega TODO en PaymentService. */
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) { this.service = service; }

    @PostMapping
    public PaymentResult pay(@RequestHeader("Idempotency-Key") String key,
                             @Valid @RequestBody CreatePaymentBody b) {
        return service.pay(new PaymentRequest(key, b.amount(), b.currency(), b.method()));
    }

    @PostMapping("/{id}/capture")
    public PaymentResult capture(@PathVariable String id) { return service.capture(id); }

    @PostMapping("/{id}/refund")
    public PaymentResult refund(@PathVariable String id,
                                @RequestParam(required = false) BigDecimal amount) {
        return service.refund(id, amount);
    }
}
