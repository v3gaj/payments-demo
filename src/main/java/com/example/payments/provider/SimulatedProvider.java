package com.example.payments.provider;

import com.example.payments.model.PaymentResult;
import com.example.payments.model.PaymentStatus;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;
import org.springframework.stereotype.Component;

/** Implementación de prueba. Reemplazar por StripeProvider/TilopayProvider (HTTP + timeout + circuit breaker). */
@Component
public class SimulatedProvider implements PaymentProvider {

    public PaymentResult authorize(String token, BigDecimal amount, Currency currency) {
        if ("tok_declined".equals(token))
            return new PaymentResult(UUID.randomUUID().toString(), PaymentStatus.FAILED, null, "card_declined");
        return new PaymentResult(UUID.randomUUID().toString(), PaymentStatus.AUTHORIZED, "sim_" + UUID.randomUUID(), null);
    }
    public PaymentResult capture(String id, BigDecimal amount) {
        return new PaymentResult(id, PaymentStatus.CAPTURED, "sim_cap_" + id, null);
    }
    public PaymentResult refund(String id, BigDecimal amount) {
        return new PaymentResult(id, PaymentStatus.REFUNDED, "sim_ref_" + id, null);
    }
}
