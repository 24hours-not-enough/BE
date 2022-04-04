package com.example.trip.advice.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String msg) {
        super(msg);
    }

    public RefreshTokenNotFoundException() {
        super();
    }
}
