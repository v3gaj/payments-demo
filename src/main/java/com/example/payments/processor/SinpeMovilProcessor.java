package com.example.payments.processor;

import com.example.payments.model.*;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * SINPE Móvil: pago inmediato -> NO implementa Capturable. Sí Refundable.
 * Con @Component el Registry lo detecta solo; no se toca ninguna otra clase.
 */
@Component
public class SinpeMovilProcessor implements PaymentProcessor, Refundable {

    public PaymentMethodType type() { return PaymentMethodType.SINPE_MOVIL; }

    public PaymentResult authorize(PaymentRequest req) {
        var d = (SinpeDetails) req.method();
        // Aquí: llamada al banco/PSP con timeout + circuit breaker (Resilience4j)
        return new PaymentResult(UUID.randomUUID().toString(), PaymentStatus.CAPTURED, "sinpe_" + d.phone(), null);
    }
    public PaymentResult refund(String id, BigDecimal amt) {
        return new PaymentResult(id, PaymentStatus.REFUNDED, "sinpe_ref_" + id, null);
    }
}
