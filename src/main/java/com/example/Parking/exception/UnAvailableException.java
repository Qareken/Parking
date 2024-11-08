package com.example.Parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnAvailableException extends RuntimeException {
    public UnAvailableException (String message){
        super(message);
    }
}
