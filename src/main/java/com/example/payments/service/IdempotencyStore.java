package com.example.payments.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;

/**
 * Misma Idempotency-Key => mismo resultado, sin volver a cobrar.
 * Versión en memoria para el demo. En producción: tabla con UNIQUE(key) o Redis (SETNX) con TTL.
 */
@Component
public class IdempotencyStore {
    private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T execute(String key, Supplier<T> action) {
        return (T) cache.computeIfAbsent(key, k -> action.get());
    }
}
