package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.CheckUsernameDto;
import com.example.trip.dto.GoogleLoginRequestDto;
import com.example.trip.dto.KakaoLoginRequestDto;
import com.example.trip.response.CheckUsernameSuccess;
import com.example.trip.response.LoginSuccess;
import com.example.trip.response.RegisterUserInfoSuccess;
import com.example.trip.service.SocialLoginServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final SocialLoginServiceImpl socialLoginServiceImpl;

    // 카카오 로그인
    @GetMapping("/api/kakaologin")
    public ResponseEntity<LoginSuccess> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        KakaoLoginRequestDto loginRequestDto = socialLoginServiceImpl.kakaoLogin(code);
        return new ResponseEntity<>(new LoginSuccess("success", "카카오 로그인 성공",
                socialLoginServiceImpl.checkKakaoIsFirstLogin(loginRequestDto),
                loginRequestDto.getEmail(),
                socialLoginServiceImpl.issueKakaoJwtToken(loginRequestDto)), HttpStatus.OK);
    }

    // 구글 로그인
    @GetMapping("/api/googlelogin")
    public ResponseEntity<LoginSuccess> googleLogin(@RequestParam String code) throws JsonProcessingException {
        GoogleLoginRequestDto loginRequestDto = socialLoginServiceImpl.googleLogin(code);
        return new ResponseEntity<>(new LoginSuccess("success", "구글 로그인 성공",
                socialLoginServiceImpl.checkGoogleIsFirstLogin(loginRequestDto),
                loginRequestDto.getEmail(),
                socialLoginServiceImpl.issueGoogleJwtToken(loginRequestDto)), HttpStatus.OK);
    }

    // 회원가입 시 추가 정보
    @PostMapping(value = "/api/login/userinfo", consumes = {"multipart/form-data"})
    public ResponseEntity<RegisterUserInfoSuccess> registerMoreUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                        @RequestPart String username,
                                                                        @RequestPart MultipartFile file) {
        socialLoginServiceImpl.registerMoreUserInfo(userDetails, username, file);
        return new ResponseEntity<>(new RegisterUserInfoSuccess("success", "정식 회원가입 완료되었습니다."), HttpStatus.OK);
    }

    @PostMapping("/api/username")
    public ResponseEntity<CheckUsernameSuccess> checkUsername(@RequestBody CheckUsernameDto checkUsernameDto) {
        socialLoginServiceImpl.checkUsername(checkUsernameDto.getUsername());
        return new ResponseEntity<>(new CheckUsernameSuccess("success", "사용할 수 있는 닉네임입니다."), HttpStatus.OK);
    }
}
