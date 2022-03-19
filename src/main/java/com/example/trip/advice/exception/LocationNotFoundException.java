package com.example.trip.advice.exception;

public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException(String msg) {
        super(msg);
    }

    public LocationNotFoundException() {
        super();
    }
}
