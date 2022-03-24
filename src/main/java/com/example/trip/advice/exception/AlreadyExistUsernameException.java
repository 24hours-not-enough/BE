package com.example.trip.advice.exception;

public class AlreadyExistUsernameException extends RuntimeException {

    public AlreadyExistUsernameException(String msg) {
        super(msg);
    }

    public AlreadyExistUsernameException() {
        super();
    }
}
