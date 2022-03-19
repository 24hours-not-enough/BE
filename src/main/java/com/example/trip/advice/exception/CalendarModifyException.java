package com.example.trip.advice.exception;

public class CalendarModifyException extends RuntimeException {

    public CalendarModifyException(String msg) {
        super(msg);
    }

    public CalendarModifyException() {
        super();
    }
}
