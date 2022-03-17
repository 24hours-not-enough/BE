package com.example.trip.advice.exception;

public class CalendarNotFoundException extends RuntimeException {

    public CalendarNotFoundException(String msg) {
        super(msg);
    }

    public CalendarNotFoundException() {
        super();
    }
}
