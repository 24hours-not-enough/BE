package com.example.trip.advice.exception;

public class CheckListModifyException extends RuntimeException {

    public CheckListModifyException(String msg) {
        super(msg);
    }

    public CheckListModifyException() {
        super();
    }
}
