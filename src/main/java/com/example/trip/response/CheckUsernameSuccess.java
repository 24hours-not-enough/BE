package com.example.trip.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckUsernameSuccess {
    private String result;
    private String msg;
}
