package com.femt.inventory_management.exceptions;

public class VentasNotFoundException extends RuntimeException{
    public VentasNotFoundException(String mensaje){
        super(mensaje);
    }
}
