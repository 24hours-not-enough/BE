package com.example.trip.service;

import com.example.trip.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SocialLoginService {

    KakaoLoginRequestDto kakaoLogin(String code, SignupRequestDto signupRequestDto) throws JsonProcessingException;

    String getKakaoAccessToken(String code) throws JsonProcessingException;

    KakaoUserInfoDto getKaKaoUserInfo(String accessToken) throws JsonProcessingException;

    void kakaoRegister(KakaoUserInfoDto kakaoUserInfo, SignupRequestDto signupRequestDto);

    GoogleLoginRequestDto googleLogin(String code, SignupRequestDto requestDto) throws JsonProcessingException;

    String getGoogleAccessToken(String code) throws JsonProcessingException;

    GoogleUserInfoDto getGoogleUserInfo(String accessToken) throws JsonProcessingException;

    void googleRegister(GoogleUserInfoDto googleUserInfo, SignupRequestDto requestDto);

    LoginResponseDto issueKakaoJwtToken(KakaoLoginRequestDto loginRequestDto);

    LoginResponseDto issueGoogleJwtToken(GoogleLoginRequestDto loginRequestDto);





}
