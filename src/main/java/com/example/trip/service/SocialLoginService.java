package com.example.trip.service;

import com.example.trip.domain.User;
import com.example.trip.dto.request.TokenRequestDto;
import com.example.trip.dto.response.TokenResponseDto;
import com.example.trip.dto.response.UserResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SocialLoginService {

    UserResponseDto.KakaoLogin kakaoLogin(String code) throws JsonProcessingException;

    String getKakaoAccessToken(String code) throws JsonProcessingException;

    UserResponseDto.KakaoUser getKaKaoUserInfo(String accessToken) throws JsonProcessingException;

    User kakaoRegister(UserResponseDto.KakaoUser kakaoUserInfo);

    UserResponseDto.GoogleLogin googleLogin(String code) throws JsonProcessingException;

    String getGoogleAccessToken(String code) throws JsonProcessingException;

    UserResponseDto.GoogleUser getGoogleUserInfo(String accessToken) throws JsonProcessingException;

    User googleRegister(UserResponseDto.GoogleUser googleUserInfo);

    UserResponseDto.TokenInfo issueToken(User user, HttpServletResponse response);

    UserResponseDto.UserInfo registerProfile(String socialaccountId, String username, MultipartFile file) throws IOException;

    boolean checkLoginFirst(User user);

    void checkUsername(String username);

    UserResponseDto.UserInfo sendUserInfo(User user);

    UserResponseDto.UserInfo searchUser(String username);

    void deleteAccount(String socialaccountId);

    void checkSameUsername(String username);

    TokenResponseDto reissueToken(TokenRequestDto requestDto);

    User getUser(String socialaccountId);
}
