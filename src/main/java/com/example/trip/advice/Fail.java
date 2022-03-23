package com.example.trip.advice;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fail {

    private String result;
    private String msg;

    public Fail(String msg) {
        this.result = "fail";
        this.msg = msg;
    }

}

