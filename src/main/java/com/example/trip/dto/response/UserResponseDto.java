package com.example.trip.dto.response;

import com.example.trip.domain.Image;
import com.example.trip.domain.Role;
import com.example.trip.domain.User;
import com.example.trip.dto.response.FeedLocationResponseDto.BookMark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Builder
    @Getter
    public static class User {
        private String result;
        private String msg;
        private com.example.trip.dto.response.queryprojection.UserInfo userInfo;
        private List<BookMark> bookmark;

    }

    @Builder
    @Getter
    public static class UserInfo {
        private Long userId;
        private String userName;
        private String userProfileImg;
    }

    @Getter
    @Builder
    public static class KakaoUser {
        private String kakaoId;
        private String email;
    }

    @Getter
    @Builder
    public static class KakaoLogin {
        private String kakaoId;
        private String email;
        private com.example.trip.domain.User user;
    }

    @Builder
    @Getter
    public static class GoogleUser {
        private String googleId;
        private String email;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GoogleLogin {
        private String googleId;
        private String email;
        private com.example.trip.domain.User user;
    }

    @Builder
    @Getter
    public static class TokenInfo {
        private String access_token;
        private String refresh_token;
    }

    @Builder
    @Getter
    public static class Response {
        private String result;
        private String msg;
        private Object data;
    }

    @Getter
    @Builder
    public static class ResponseNoData {
        private String result;
        private String msg;
    }

    @Builder
    @Getter
    public static class LoginSuccess {
        private String result;
        private String msg;
        private boolean first;
        private TokenInfo tokens;
        private UserInfo userInfo;
    }

    @Builder
    @Getter
    public static class Notification {
        private String result;
        private String msg;
        private List<com.example.trip.redis.notification.Notification> notification;
    }

    @Builder
    @Getter
    public static class reissueToken {
        private String accessToken;
        private String refreshToken;
    }
}
