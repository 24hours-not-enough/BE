package com.example.trip.dto.sociallogin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GoogleUserInfoDto {
    private String googleId;
    private String email;

}
