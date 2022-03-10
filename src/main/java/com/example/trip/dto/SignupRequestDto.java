package com.example.trip.dto;

import com.example.trip.domain.Image;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    private Image image;
    private String username;
}