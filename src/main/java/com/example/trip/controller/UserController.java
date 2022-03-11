package com.example.trip.controller;

import com.example.trip.dto.GoogleLoginRequestDto;
import com.example.trip.dto.KakaoLoginRequestDto;
import com.example.trip.dto.SignupRequestDto;
import com.example.trip.response.LoginSuccess;
import com.example.trip.service.SocialLoginServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final SocialLoginServiceImpl socialLoginServiceImpl;

    // 카카오 로그인
    @GetMapping("/api/kakaologin")
    public ResponseEntity<LoginSuccess> kakaoLogin(@RequestParam String code, @RequestBody SignupRequestDto signupRequestDto) throws JsonProcessingException {
        KakaoLoginRequestDto loginRequestDto = socialLoginServiceImpl.kakaoLogin(code, signupRequestDto);
        return new ResponseEntity<>(new LoginSuccess("success", "로그인 성공", socialLoginServiceImpl.issueKakaoJwtToken(loginRequestDto)), HttpStatus.OK);
    }

    // 구글 로그인
    @GetMapping("/api/googlelogin")
    public ResponseEntity<LoginSuccess> googleLogin(@RequestParam String code, @RequestBody SignupRequestDto signupRequestDto) throws JsonProcessingException {
        GoogleLoginRequestDto loginRequestDto = socialLoginServiceImpl.googleLogin(code, signupRequestDto);
        return new ResponseEntity<>(new LoginSuccess("success", "로그인 성공", socialLoginServiceImpl.issueGoogleJwtToken(loginRequestDto)), HttpStatus.OK);
    }
}
