package com.example.trip.controller;

import com.example.trip.dto.GoogleLoginRequestDto;
import com.example.trip.dto.KakaoLoginRequestDto;
import com.example.trip.dto.SignupRequestDto;
import com.example.trip.response.LoginSuccess;
import com.example.trip.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/api/kakaologin")
    public ResponseEntity<LoginSuccess> kakaoLogin(@RequestParam String code, @RequestBody SignupRequestDto signupRequestDto) throws JsonProcessingException {
        KakaoLoginRequestDto loginRequestDto = userService.kakaoLogin(code, signupRequestDto);
        return new ResponseEntity<>(new LoginSuccess("success", "로그인 성공", userService.issueKakaoJwtToken(loginRequestDto)), HttpStatus.OK);
    }

    @GetMapping("/api/googlelogin")
    public ResponseEntity<LoginSuccess> googleLogin(@RequestParam String code, @RequestBody SignupRequestDto signupRequestDto) throws JsonProcessingException {
        GoogleLoginRequestDto loginRequestDto = userService.googleLogin(code, signupRequestDto);
        return new ResponseEntity<>(new LoginSuccess("success", "로그인 성공", userService.issueGoogleJwtToken(loginRequestDto)), HttpStatus.OK);
    }
}
