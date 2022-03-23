package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.*;
import com.example.trip.response.*;
import com.example.trip.service.BookMarkService;
import com.example.trip.service.RedisService;
import com.example.trip.service.SocialLoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final SocialLoginService socialLoginService;
    private final RedisService redisService;

    private final BookMarkService bookMarkService;

    // 카카오 로그인
    @GetMapping("/api/kakaologin")
    public ResponseEntity<LoginSuccess> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        UserResponseDto.KakaoLogin loginRequestDto = socialLoginService.kakaoLogin(code);
        return new ResponseEntity<>(new LoginSuccess("success", "카카오 로그인 성공",
                socialLoginService.checkKakaoIsFirstLogin(loginRequestDto),
                loginRequestDto.getEmail(),
                socialLoginService.issueKakaoJwtToken(loginRequestDto, response),
                socialLoginService.sendKakaoUserBasicInfo(loginRequestDto)), HttpStatus.OK);
    }

    // 구글 로그인
    @GetMapping("/api/googlelogin")
    public ResponseEntity<LoginSuccess> googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        UserResponseDto.GoogleLogin loginRequestDto = socialLoginService.googleLogin(code);
        return new ResponseEntity<>(new LoginSuccess("success", "구글 로그인 성공",
                socialLoginService.checkGoogleIsFirstLogin(loginRequestDto),
                loginRequestDto.getEmail(),
                socialLoginService.issueGoogleJwtToken(loginRequestDto, response),
                socialLoginService.sendGoogleUserBasicInfo(loginRequestDto)), HttpStatus.OK);
    }

    // 회원가입 시 추가 정보
    @PostMapping(value = "/api/login/userinfo", consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponseDto.Response> registerMoreUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                        @RequestPart String username,
                                                                        @RequestPart MultipartFile file) throws IOException {
        UserResponseDto.UserProfile userBasicInfo = socialLoginService.registerMoreUserInfo(userDetails.getUser().getSocialaccountId(), username, file);
        return new ResponseEntity<>(UserResponseDto.Response.builder()
                                                        .result("success")
                                                        .msg("정식 회원가입 완료되었습니다.")
                                                        .data(userBasicInfo).build(), HttpStatus.OK);
    }

    @PostMapping("/api/username")
    public ResponseEntity<UserResponseDto.ResponseNoData> checkUsername(@Valid @RequestBody UserRequestDto.CheckUsername checkUsername) {
        socialLoginService.checkUsername(checkUsername.getUsername());
        return new ResponseEntity<>(UserResponseDto.ResponseNoData.builder()
                .result("success").msg("사용가능한 닉네임입니다.").build(), HttpStatus.OK);
    }

    // 유저 정보 전달
    @GetMapping("/user/userprofileinfo")
    public ResponseEntity<UserProfileInfo> sendUserProfileInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto.UserProfile userBasicInfo = socialLoginService.sendUserProfileInfo(userDetails.getUser().getSocialaccountId());
        return new ResponseEntity<>(new UserProfileInfo(userBasicInfo), HttpStatus.OK);
    }

    @GetMapping("/api/user/{nickname}")
    public ResponseEntity<UserResponseDto.Response> searchUserInvite(@PathVariable String nickname) {
        UserResponseDto.invite invite = socialLoginService.searchUserInvite(nickname);
        return new ResponseEntity<>(UserResponseDto.Response.builder()
                .result("success")
                .msg("검색한 해당 사용자가 존재합니다.")
                .data(invite).build(), HttpStatus.OK);
    }

    // 로그아웃
    @GetMapping("/api/logout")
    public ResponseEntity<UserResponseDto.ResponseNoData> logout(HttpServletRequest request) {
        redisService.delValues(request.getHeader("refreshToken"));
        return new ResponseEntity<>(UserResponseDto.ResponseNoData.builder()
                .result("success").msg("로그아웃 성공!").build(), HttpStatus.OK);
    }

    // 회원탈퇴
    @PostMapping("/api/withdrawal")
    public ResponseEntity<UserResponseDto.ResponseNoData> deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        socialLoginService.deleteAccount(userDetails.getUser().getSocialaccountId());
        return new ResponseEntity<>(UserResponseDto.ResponseNoData.builder()
                .result("success")
                .msg("회원 탈퇴되었습니다.").build(), HttpStatus.OK);
    }

//    @GetMapping("/api/user")
//    public ResponseEntity<UserResponseDto.Response> userInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        bookMarkService.findBookMarkPlaces(userDetails.getUser().getId());
//        return new ResponseEntity<>(UserResponseDto.Response.builder()
//                .result("success")
//                .msg("유저 정보입니다.")
//                .data().build()
//                , HttpStatus.OK);
//    }
}
