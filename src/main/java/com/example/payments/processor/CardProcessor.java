package com.example.payments.processor;

import com.example.payments.model.*;
import com.example.payments.provider.PaymentProvider;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * Tarjeta: contrato base + Capturable + Refundable.
 * DELEGA en PaymentProvider (separa MÉTODO de PROVEEDOR).
 */
@Component
public class CardProcessor implements PaymentProcessor, Capturable, Refundable {
    private final PaymentProvider provider;

    public CardProcessor(PaymentProvider provider) { this.provider = provider; }

    public PaymentMethodType type() { return PaymentMethodType.CARD; }

    public PaymentResult authorize(PaymentRequest req) {
        var d = (CardDetails) req.method(); // seguro: el Registry garantizó type()==CARD
        return provider.authorize(d.token(), req.amount(), req.currency());
    }
    public PaymentResult capture(String id, BigDecimal amt) { return provider.capture(id, amt); }
    public PaymentResult refund(String id, BigDecimal amt)  { return provider.refund(id, amt); }
}
