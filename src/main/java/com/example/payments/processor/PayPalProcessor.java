package com.example.payments.processor;

import com.example.payments.model.*;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Component;

/** PayPal: autoriza (order aprobada), captura después y permite reembolso. */
@Component
public class PayPalProcessor implements PaymentProcessor, Capturable, Refundable {

    public PaymentMethodType type() { return PaymentMethodType.PAYPAL; }

    public PaymentResult authorize(PaymentRequest req) {
        var d = (PayPalDetails) req.method();
        // Aquí: PayPal Orders API (authorize) con d.orderId()
        return new PaymentResult(UUID.randomUUID().toString(), PaymentStatus.AUTHORIZED, "pp_" + d.orderId(), null);
    }
    public PaymentResult capture(String id, BigDecimal amt) {
        return new PaymentResult(id, PaymentStatus.CAPTURED, "pp_cap_" + id, null);
    }
    public PaymentResult refund(String id, BigDecimal amt) {
        return new PaymentResult(id, PaymentStatus.REFUNDED, "pp_ref_" + id, null);
    }
}
