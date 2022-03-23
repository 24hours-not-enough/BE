package com.example.trip.response;

import com.example.trip.dto.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProfileInfo {
    private UserResponseDto.UserProfile userbasicinfo;
}
