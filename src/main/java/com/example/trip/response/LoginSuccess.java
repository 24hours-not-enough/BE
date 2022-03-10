package com.example.trip.response;

import com.example.trip.dto.LoginResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginSuccess {
    private String result;
    private String msg;
    private LoginResponseDto dto;
}
