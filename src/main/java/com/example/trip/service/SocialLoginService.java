package com.example.trip.service;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.domain.User;
import com.example.trip.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

public interface SocialLoginService {

    KakaoLoginRequestDto kakaoLogin(String code) throws JsonProcessingException;

    String getKakaoAccessToken(String code) throws JsonProcessingException;

    KakaoUserInfoDto getKaKaoUserInfo(String accessToken) throws JsonProcessingException;

    User kakaoRegister(KakaoUserInfoDto kakaoUserInfo);

    GoogleLoginRequestDto googleLogin(String code) throws JsonProcessingException;

    String getGoogleAccessToken(String code) throws JsonProcessingException;

    GoogleUserInfoDto getGoogleUserInfo(String accessToken) throws JsonProcessingException;

    User googleRegister(GoogleUserInfoDto googleUserInfo);

    LoginResponseDto issueKakaoJwtToken(KakaoLoginRequestDto loginRequestDto);

    LoginResponseDto issueGoogleJwtToken(GoogleLoginRequestDto loginRequestDto);

    void registerMoreUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, String username, MultipartFile file);

    boolean checkKakaoIsFirstLogin(KakaoLoginRequestDto loginRequestDto);

    boolean checkGoogleIsFirstLogin(GoogleLoginRequestDto loginRequestDto);

    void checkUsername(String username);
}
