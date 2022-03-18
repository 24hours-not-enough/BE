package com.example.trip.service;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.domain.User;
import com.example.trip.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SocialLoginService {

    KakaoLoginRequestDto kakaoLogin(String code) throws JsonProcessingException;

    String getKakaoAccessToken(String code) throws JsonProcessingException;

    KakaoUserInfoDto getKaKaoUserInfo(String accessToken) throws JsonProcessingException;

    User kakaoRegister(KakaoUserInfoDto kakaoUserInfo);

    GoogleLoginRequestDto googleLogin(String code) throws JsonProcessingException;

    String getGoogleAccessToken(String code) throws JsonProcessingException;

    GoogleUserInfoDto getGoogleUserInfo(String accessToken) throws JsonProcessingException;

    User googleRegister(GoogleUserInfoDto googleUserInfo);

    LoginResponseDto issueKakaoJwtToken(KakaoLoginRequestDto loginRequestDto, HttpServletResponse response);

    LoginResponseDto issueGoogleJwtToken(GoogleLoginRequestDto loginRequestDto, HttpServletResponse response);

    UserBasicInfoResponseDto registerMoreUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, String username, MultipartFile file) throws IOException;

    boolean checkKakaoIsFirstLogin(KakaoLoginRequestDto loginRequestDto);

    boolean checkGoogleIsFirstLogin(GoogleLoginRequestDto loginRequestDto);

    void checkUsername(String username);

    UserBasicInfoResponseDto sendKakaoUserBasicInfo(KakaoLoginRequestDto loginRequestDto);

    UserBasicInfoResponseDto sendGoogleUserBasicInfo(GoogleLoginRequestDto loginRequestDto);

    UserBasicInfoResponseDto sendUserProfileInfo(@AuthenticationPrincipal UserDetailsImpl userDetails);

    SearchUserInviteResponseDto searchUserInvite(String username);
}
