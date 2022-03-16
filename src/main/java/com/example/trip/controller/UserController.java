package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.*;
import com.example.trip.dto.sociallogin.CheckUsernameDto;
import com.example.trip.dto.sociallogin.GoogleLoginRequestDto;
import com.example.trip.dto.sociallogin.KakaoLoginRequestDto;
import com.example.trip.response.*;
import com.example.trip.service.SocialLoginServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
                socialLoginServiceImpl.issueKakaoJwtToken(loginRequestDto),
                socialLoginServiceImpl.sendKakaoUserBasicInfo(loginRequestDto)), HttpStatus.OK);
    }

    // 구글 로그인
    @GetMapping("/api/googlelogin")
    public ResponseEntity<LoginSuccess> googleLogin(@RequestParam String code) throws JsonProcessingException {
        GoogleLoginRequestDto loginRequestDto = socialLoginServiceImpl.googleLogin(code);
        return new ResponseEntity<>(new LoginSuccess("success", "구글 로그인 성공",
                socialLoginServiceImpl.checkGoogleIsFirstLogin(loginRequestDto),
                loginRequestDto.getEmail(),
                socialLoginServiceImpl.issueGoogleJwtToken(loginRequestDto),
                socialLoginServiceImpl.sendGoogleUserBasicInfo(loginRequestDto)), HttpStatus.OK);
    }

    // 회원가입 시 추가 정보
    @PostMapping(value = "/api/login/userinfo", consumes = {"multipart/form-data"})
    public ResponseEntity<RegisterUserInfoSuccess> registerMoreUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                        @RequestPart String username,
                                                                        @RequestPart MultipartFile file) throws IOException {
        UserBasicInfoResponseDto userBasicInfo = socialLoginServiceImpl.registerMoreUserInfo(userDetails, username, file);
        return new ResponseEntity<>(new RegisterUserInfoSuccess("success", "정식 회원가입 완료되었습니다.", userBasicInfo), HttpStatus.OK);
    }

    @PostMapping("/api/username")
    public ResponseEntity<CheckUsernameSuccess> checkUsername(@RequestBody CheckUsernameDto checkUsernameDto) {
        socialLoginServiceImpl.checkUsername(checkUsernameDto.getUsername());
        return new ResponseEntity<>(new CheckUsernameSuccess("success", "사용할 수 있는 닉네임입니다."), HttpStatus.OK);
    }

    @GetMapping("/user/userprofileinfo")
    public ResponseEntity<UserProfileInfo> sendUserProfileInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserBasicInfoResponseDto userBasicInfo = socialLoginServiceImpl.sendUserProfileInfo(userDetails);
        return new ResponseEntity<>(new UserProfileInfo(userBasicInfo), HttpStatus.OK);
    }

    @GetMapping("/api/user/{nickname}")
    public ResponseEntity<SearchUserInviteSuccess> searchUserInvite(@PathVariable String nickname) {
        SearchUserInviteResponseDto searchUserInviteResponseDto = socialLoginServiceImpl.searchUserInvite(nickname);
        return new ResponseEntity<>(new SearchUserInviteSuccess("success", "검색한 해당 사용자가 존재합니다.", searchUserInviteResponseDto), HttpStatus.OK);
    }
}
