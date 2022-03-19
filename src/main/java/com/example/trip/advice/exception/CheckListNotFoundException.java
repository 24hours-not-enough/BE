package com.example.trip.advice.exception;

public class CheckListNotFoundException extends RuntimeException {

    public CheckListNotFoundException(String msg) {
        super(msg);
    }

    public CheckListNotFoundException() {
        super();
    }
}
