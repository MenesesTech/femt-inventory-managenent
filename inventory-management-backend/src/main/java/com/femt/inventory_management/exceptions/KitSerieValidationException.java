package com.femt.inventory_management.exceptions;

public class KitSerieValidationException extends RuntimeException{
    private final String arg;

    public KitSerieValidationException(String mensaje, String arg){
        super(mensaje);
        this.arg = arg;
    }

    public String getArg(){
        return arg;
    }
}
