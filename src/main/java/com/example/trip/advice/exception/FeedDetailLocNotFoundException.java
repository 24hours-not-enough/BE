package com.example.trip.advice.exception;

public class FeedDetailLocNotFoundException extends RuntimeException  {
    public FeedDetailLocNotFoundException(String msg) {
        super(msg);
    }

    public FeedDetailLocNotFoundException() {
        super();
    }
}
