package com.example.trip.advice.exception;

public class CalendarDetailsAlreadyExistException extends RuntimeException  {
    public CalendarDetailsAlreadyExistException(String msg) {
        super(msg);
    }

    public CalendarDetailsAlreadyExistException() {
        super();
    }
}
