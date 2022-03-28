package com.example.trip.service;

import com.example.trip.dto.response.UserResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    UserResponseDto.GetUser findUser(Long userId);

    void registerLog(HttpServletRequest request, UserResponseDto.KakaoLogin loginRequestDto);
}
