package com.example.trip.advice.exception;

public class TitleNotFoundException extends RuntimeException {

    public TitleNotFoundException(String msg) {
        super(msg);
    }

    public TitleNotFoundException() {
        super();
    }
}
