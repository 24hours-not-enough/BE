package com.example.trip.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KakaoLoginRequestDto {
    private String email;
    private Long kakaoId;
}
