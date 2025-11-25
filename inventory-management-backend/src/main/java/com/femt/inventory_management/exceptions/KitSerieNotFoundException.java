package com.femt.inventory_management.exceptions;

public class KitSerieNotFoundException extends RuntimeException{
    public KitSerieNotFoundException(String mensaje){
        super(mensaje);
    }
}
