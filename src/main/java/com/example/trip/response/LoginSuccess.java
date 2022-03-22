package com.example.trip.response;

import com.example.trip.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginSuccess {
    private String result;
    private String msg;
    private boolean isfirst;
    private String email;
    private UserResponseDto.TokenInfo tokens;
    private UserResponseDto.UserProfile userbasicinfo;
}
