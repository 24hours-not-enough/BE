package com.example.trip.response;

import com.example.trip.dto.UserBasicInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterUserInfoSuccess {
    private String result;
    private String msg;
    private UserBasicInfoResponseDto userbasicinfo;
}
