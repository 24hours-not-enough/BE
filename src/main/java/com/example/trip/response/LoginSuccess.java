package com.example.trip.response;

import com.example.trip.dto.LoginResponseDto;
import com.example.trip.dto.UserBasicInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginSuccess {
    private String result;
    private String msg;
    private boolean isfirst;
    private String email;
    private LoginResponseDto tokens;
    private UserBasicInfoResponseDto userbasicinfo;
}
