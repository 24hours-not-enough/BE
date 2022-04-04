package com.example.trip.advice.exception;

public class CheckListAlreadyExistException extends RuntimeException  {
    public CheckListAlreadyExistException(String msg) {
        super(msg);
    }

    public CheckListAlreadyExistException() {
        super();
    }
}
