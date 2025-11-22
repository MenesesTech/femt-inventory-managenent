package com.femt.inventory_management.exceptions;

public class DimensionNotFoundException extends RuntimeException{
    public DimensionNotFoundException(String mensaje){
        super(mensaje);
    }
}
