package com.mercadolibre.challenge.controller;

import com.mercadolibre.challenge.dto.ValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {

        return ResponseEntity.badRequest().body(Collections.singletonMap("error", ex.getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> validationErrorHandler(ValidationError ex) {

        return ResponseEntity.badRequest().body(Collections.singletonMap("error", ex.getMessage()));
    }

}
