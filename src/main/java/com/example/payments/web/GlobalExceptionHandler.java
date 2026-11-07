package com.example.payments.web;

import com.example.payments.service.*;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** Mapea excepciones del dominio a códigos HTTP. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String, String> notFound(RuntimeException e) { return Map.of("error", e.getMessage()); }

    @ExceptionHandler({UnsupportedPaymentMethodException.class, OperationNotSupportedException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    Map<String, String> unsupported(RuntimeException e) { return Map.of("error", e.getMessage()); }

    @ExceptionHandler(InvalidPaymentStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    Map<String, String> conflict(RuntimeException e) { return Map.of("error", e.getMessage()); }
}
