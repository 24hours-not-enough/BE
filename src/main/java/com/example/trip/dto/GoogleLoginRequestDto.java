package com.example.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GoogleLoginRequestDto {
    private String email;
    private String googleId;
}
