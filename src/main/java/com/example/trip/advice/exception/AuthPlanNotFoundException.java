package com.example.trip.advice.exception;

public class AuthPlanNotFoundException extends RuntimeException {

    public AuthPlanNotFoundException(String msg) {
        super(msg);
    }

    public AuthPlanNotFoundException() {
        super();
    }
}
