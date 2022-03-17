package com.example.trip.advice.exception;

public class PlanNotFoundException extends RuntimeException {

    public PlanNotFoundException(String msg) {
        super(msg);
    }

    public PlanNotFoundException() {
        super();
    }
}
