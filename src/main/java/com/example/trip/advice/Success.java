package com.example.trip.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Success {
    private boolean success;

    private String msg;
}