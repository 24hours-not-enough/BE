package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.request.TokenRequestDto;
import com.example.trip.dto.request.UserRequestDto;
import com.example.trip.dto.response.FeedLocationResponseDto.BookMark;
import com.example.trip.dto.response.TokenResponseDto;
import com.example.trip.dto.response.UserResponseDto;
import com.example.trip.dto.response.UserResponseDto.*;
import com.example.trip.redis.notification.Notification;
import com.example.trip.service.BookMarkService;
import com.example.trip.service.SocialLoginService;
import com.example.trip.service.RedisService;
import com.example.trip.service.UserService;
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
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final SocialLoginService socialLoginService;
    private final RedisService redisService;
    private final BookMarkService bookMarkService;
    private final UserService userService;

    // 카카오 로그인
    @GetMapping("/api/kakaologin")
    public ResponseEntity<LoginSuccess> kakaoLogin(@RequestParam String code, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException {
        UserResponseDto.KakaoLogin dto = socialLoginService.kakaoLogin(code);
        userService.registerLog(request, dto.getUser());
        return new ResponseEntity<>(LoginSuccess.builder()
                .result("success")
                .msg("카카오 로그인 성공")
                .first(socialLoginService.checkLoginFirst(dto.getUser()))
                .tokens(socialLoginService.issueToken(dto.getUser(), response))
                .userInfo(socialLoginService.sendUserInfo(dto.getUser()))
                .build(), HttpStatus.OK);
    }

    // 구글 로그인
    @GetMapping("/api/googlelogin")
    public ResponseEntity<LoginSuccess> googleLogin(@RequestParam String code, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException {
        UserResponseDto.GoogleLogin dto = socialLoginService.googleLogin(code);
        userService.registerLog(request, dto.getUser());
        return new ResponseEntity<>(LoginSuccess.builder()
                .result("success")
                .msg("구글 로그인 성공")
                .first(socialLoginService.checkLoginFirst(dto.getUser()))
                .tokens(socialLoginService.issueToken(dto.getUser(), response))
                .userInfo(socialLoginService.sendUserInfo(dto.getUser()))
                .build(), HttpStatus.OK);
    }

    // 회원가입 시 추가 정보
    @PostMapping(value = "/api/login/userinfo", consumes = {"multipart/form-data"})
    public ResponseEntity<Response> registerMoreUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         @RequestPart String username,
                                                         @RequestPart MultipartFile file) throws IOException {
        socialLoginService.checkSameUsername(username);
        UserResponseDto.UserInfo userBasicInfo = socialLoginService.registerProfile(userDetails.getUser().getSocialaccountId(), username, file);
        return new ResponseEntity<>(Response.builder()
                .result("success")
                .msg("정식 회원가입 완료되었습니다.")
                .data(userBasicInfo).build(), HttpStatus.OK);
    }

    // 아이디 중복 확인
    @PostMapping("/api/username")
    public ResponseEntity<ResponseNoData> checkUsername(@Valid @RequestBody UserRequestDto.CheckUsername checkUsername) {
        socialLoginService.checkUsername(checkUsername.getUserName());
        return new ResponseEntity<>(ResponseNoData.builder()
                .result("success").msg("사용가능한 닉네임입니다.").build(), HttpStatus.OK);
    }

    // 사용자 초대
    @GetMapping("/api/user/{nickname}")
    public ResponseEntity<Response> searchUserInvite(@PathVariable String nickname) {
        UserResponseDto.UserInfo invite = socialLoginService.searchUser(nickname);
        return new ResponseEntity<>(Response.builder()
                .result("success")
                .msg("검색한 해당 사용자가 존재합니다.")
                .data(invite).build(), HttpStatus.OK);
    }

    // 로그아웃
    @GetMapping("/api/logout")
    public ResponseEntity<ResponseNoData> logout(HttpServletRequest request) {
        redisService.delValues(request.getHeader("refreshToken"));
        return new ResponseEntity<>(ResponseNoData.builder()
                .result("success").msg("로그아웃 성공!").build(), HttpStatus.OK);
    }

    // 회원탈퇴
    @PostMapping("/api/withdrawal")
    public ResponseEntity<ResponseNoData> deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        socialLoginService.deleteAccount(userDetails.getUser().getSocialaccountId());
        return new ResponseEntity<>(ResponseNoData.builder()
                .result("success")
                .msg("회원 탈퇴되었습니다.").build(), HttpStatus.OK);
    }

    // 유저 정보 조회
    @GetMapping("/api/user")
    public ResponseEntity<UserResponseDto.User> userInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserBasic user = userService.findUser(userDetails.getUser().getId());
        List<BookMark> bookMarkPlaces = bookMarkService.findBookMarkPlaces(userDetails.getUser().getId());
        return new ResponseEntity<>(UserResponseDto.User.builder()
                .result("success")
                .msg("유저 정보입니다.")
                .userInfo(user)
                .bookmark(bookMarkPlaces).build()
                , HttpStatus.OK);
    }

    // 유저 알림 정보 조회
    @GetMapping("/api/notification")
    public ResponseEntity<UserResponseDto.Notification> getNotification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Notification> notification = userService.getNotification(userDetails.getUser().getId());
        return new ResponseEntity<>(UserResponseDto.Notification.builder()
                .result("success")
                .msg("알림 정보입니다.")
                .notification(notification)
                .build(), HttpStatus.OK);
    }

    // 토큰 재발급
    @PostMapping("/api/token")
    public ResponseEntity<reissueToken> reissueToken(@RequestBody TokenRequestDto requestDto) {
        TokenResponseDto dto = socialLoginService.reissueToken(requestDto);
        return new ResponseEntity<>(reissueToken.builder()
                .accessToken(dto.getAccessToken())
                .refreshToken(dto.getRefreshToken())
                .build(), HttpStatus.OK);
    }
}
