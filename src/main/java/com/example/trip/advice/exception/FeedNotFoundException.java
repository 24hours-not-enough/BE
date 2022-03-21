package com.example.trip.advice.exception;

public class FeedNotFoundException extends RuntimeException  {
    public FeedNotFoundException(String msg) {
        super(msg);
    }

    public FeedNotFoundException() {
        super();
    }
}

