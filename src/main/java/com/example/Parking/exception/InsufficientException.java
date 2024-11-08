package com.example.Parking.exception;

public class InsufficientException extends RuntimeException {

    public InsufficientException(String message) {
        super(message);
    }

    public InsufficientException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientException() {
        super("Insufficient funds for the booking.");
    }
}