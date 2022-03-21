package com.example.trip.advice.exception;

public class AuthLikesNotFoundException extends RuntimeException  {
    public AuthLikesNotFoundException(String msg) {
        super(msg);
    }

    public AuthLikesNotFoundException() {
        super();
    }
}


