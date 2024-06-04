package com.riwi.Simulacrum_SpringBoot_Test.util.exceptions;

public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message){
        super(message);
    }
}
