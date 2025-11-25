package com.femt.inventory_management.exceptions;

public class VentasValidationException extends RuntimeException{
    private final String field;

    public VentasValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
