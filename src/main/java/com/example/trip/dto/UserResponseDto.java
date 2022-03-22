package com.example.trip.dto;

import com.example.trip.domain.Image;
import com.example.trip.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserResponseDto {

    private Long id;

    private String email;

    private String username;

    private String password;

    private boolean memberstatus;

    private String socialaccountId;

    private Role role;

    private Image image;

    @Getter
    @AllArgsConstructor
    public static class GetKakaoUserInfo {
        private String kakaoId;
        private String email;
    }

    @AllArgsConstructor
    @Getter
    public static class GetGoogleUserInfo {
        private String googleId;
        private String email;

    }

    @AllArgsConstructor
    @Getter
    public static class KakaoLogin {
        private String email;
        private String kakaoId;
    }

    @AllArgsConstructor
    @Getter
    public static class GoogleLogin {
        private String email;
        private String googleId;
    }

    @AllArgsConstructor
    @Getter
    public static class UserProfile {
        private String username;
        private String profileImg;
    }

    @AllArgsConstructor
    @Getter
    public static class TokenInfo {
        private String access_token;
        //    private String refresh_token;
    }

    @Builder
    @Getter
    public static class Response {
        private String result;
        private String msg;
        private Object data;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class ResponseNoData {
        private String result;
        private String msg;
    }

    @AllArgsConstructor
    @Getter
    public static class invite {
        private String file_store_course;
        private String nickname;
        private Long user_id;
    }
}
