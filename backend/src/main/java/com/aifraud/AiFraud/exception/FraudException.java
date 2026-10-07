package com.aifraud.AiFraud.exception;

public class FraudException extends RuntimeException {
    
    public FraudException(String message) {
        super(message);
    }
    
    public FraudException(String message, Throwable cause) {
        super(message, cause);
    }
}