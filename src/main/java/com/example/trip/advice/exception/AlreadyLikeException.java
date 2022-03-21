package com.example.trip.advice.exception;

public class AlreadyLikeException extends RuntimeException  {
    public AlreadyLikeException(String msg) {
        super(msg);
    }

    public AlreadyLikeException() {
        super();
    }
}

