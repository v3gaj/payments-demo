package com.example.payments.processor;

import com.example.payments.model.*;
import java.util.UUID;
import org.springframework.stereotype.Component;

/** Transferencia: queda PENDING hasta confirmar el depósito (vía webhook). Solo contrato base. */
@Component
public class BankTransferProcessor implements PaymentProcessor {

    public PaymentMethodType type() { return PaymentMethodType.BANK_TRANSFER; }

    public PaymentResult authorize(PaymentRequest req) {
        var d = (BankTransferDetails) req.method();
        return new PaymentResult(UUID.randomUUID().toString(), PaymentStatus.PENDING, "bt_" + d.iban(), null);
    }
}
