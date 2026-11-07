package com.example.payments.processor;

import com.example.payments.model.PaymentMethodType;
import com.example.payments.service.UnsupportedPaymentMethodException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Puente enum -> implementación concreta.
 * Spring inyecta TODOS los beans PaymentProcessor; aquí se indexan por type().
 * Si dos processors declaran el mismo type(), la app NO arranca (fail-fast).
 * PaymentService depende de este Registry, nunca de clases concretas (DIP).
 */
@Component
public class ProcessorRegistry {
    private final Map<PaymentMethodType, PaymentProcessor> processors;

    public ProcessorRegistry(List<PaymentProcessor> all) {
        this.processors = all.stream()
            .collect(Collectors.toUnmodifiableMap(PaymentProcessor::type, Function.identity()));
    }

    public PaymentProcessor resolve(PaymentMethodType type) {
        var p = processors.get(type);
        if (p == null) throw new UnsupportedPaymentMethodException(type);
        return p;
    }
}
