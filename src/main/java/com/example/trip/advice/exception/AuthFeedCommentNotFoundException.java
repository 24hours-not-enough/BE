package com.example.trip.advice.exception;

public class AuthFeedCommentNotFoundException extends RuntimeException  {
    public AuthFeedCommentNotFoundException(String msg) {
        super(msg);
    }

    public AuthFeedCommentNotFoundException() {
        super();
    }
}
