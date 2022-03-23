package com.example.trip.service;

import com.example.trip.domain.User;
import com.example.trip.dto.response.UserResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SocialLoginService {

    UserResponseDto.KakaoLogin kakaoLogin(String code) throws JsonProcessingException;

    String getKakaoAccessToken(String code) throws JsonProcessingException;

    UserResponseDto.GetKakaoUserInfo getKaKaoUserInfo(String accessToken) throws JsonProcessingException;

    User kakaoRegister(UserResponseDto.GetKakaoUserInfo kakaoUserInfo);

    UserResponseDto.GoogleLogin googleLogin(String code) throws JsonProcessingException;

    String getGoogleAccessToken(String code) throws JsonProcessingException;

    UserResponseDto.GetGoogleUserInfo getGoogleUserInfo(String accessToken) throws JsonProcessingException;

    User googleRegister(UserResponseDto.GetGoogleUserInfo googleUserInfo);

    UserResponseDto.TokenInfo issueKakaoJwtToken(UserResponseDto.KakaoLogin loginRequestDto, HttpServletResponse response);

    UserResponseDto.TokenInfo issueGoogleJwtToken(UserResponseDto.GoogleLogin loginRequestDto, HttpServletResponse response);

    UserResponseDto.UserProfile registerMoreUserInfo(String socialaccountId, String username, MultipartFile file) throws IOException;

    boolean checkKakaoIsFirstLogin(UserResponseDto.KakaoLogin loginRequestDto);

    boolean checkGoogleIsFirstLogin(UserResponseDto.GoogleLogin loginRequestDto);

    void checkUsername(String username);

    UserResponseDto.UserProfile sendKakaoUserBasicInfo(UserResponseDto.KakaoLogin loginRequestDto);

    UserResponseDto.UserProfile sendGoogleUserBasicInfo(UserResponseDto.GoogleLogin loginRequestDto);

    UserResponseDto.UserProfile sendUserProfileInfo(String socialaccountId);

    UserResponseDto.invite searchUserInvite(String username);

    void deleteAccount(String socialaccountId);
}
