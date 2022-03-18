package com.example.trip.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteAccountSuccess {
    private String result;
    private String msg;
}
