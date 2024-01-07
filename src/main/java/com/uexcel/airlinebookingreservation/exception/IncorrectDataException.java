package com.uexcel.airlinebookingreservation.exception;


public class IncorrectDataException extends RuntimeException{
    private final String message;
    public IncorrectDataException(String message){
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
