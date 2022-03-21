package com.example.trip.advice.exception;

public class AuthFeedNotFoundException extends RuntimeException {
    public AuthFeedNotFoundException(String msg) {
        super(msg);
    }

    public AuthFeedNotFoundException() {
        super();
    }
}
