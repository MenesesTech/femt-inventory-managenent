package com.femt.inventory_management.exceptions;

public class DimensionValidationException extends RuntimeException{
    private final String arg;

    public DimensionValidationException(String mensaje, String arg){
        super(mensaje);
        this.arg = arg;
    }

    public String getArg(){
        return arg;
    }

}
