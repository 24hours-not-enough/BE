package com.example.trip.response;

import com.example.trip.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProfileInfo {
    private UserResponseDto.UserProfile userbasicinfo;
}
