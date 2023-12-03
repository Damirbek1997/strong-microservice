package com.example.authservice.exceptions;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException(String message) {
        super(message);
    }
}
