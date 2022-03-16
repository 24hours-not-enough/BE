package com.example.trip.dto.sociallogin;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SignupRequestDto {
    private MultipartFile file;
    private String username;
}