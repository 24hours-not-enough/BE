package com.example.trip.advice.exception;

public class AuthBookMarkNotFoundException extends RuntimeException  {
    public AuthBookMarkNotFoundException(String msg) {
        super(msg);
    }

    public AuthBookMarkNotFoundException() {
        super();
    }
}
