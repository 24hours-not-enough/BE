package com.example.trip.advice.exception;

public class LoginNotFoundException extends RuntimeException {

    public LoginNotFoundException(String msg) {
        super(msg);
    }

    public LoginNotFoundException() {
        super();
    }
}
