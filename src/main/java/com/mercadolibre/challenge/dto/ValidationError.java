package com.mercadolibre.challenge.dto;

public class ValidationError extends Exception {

    public ValidationError(String message) {
        super(message);
    }

}
