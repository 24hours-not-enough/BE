package com.example.trip.service;

import com.example.trip.domain.User;
import com.example.trip.dto.response.UserResponseDto;

public interface UserService {

    UserResponseDto.GetUser findUser(Long userId);
}
