package com.example.trip.dto.response;

import com.example.trip.domain.Image;
import com.example.trip.domain.Role;
import com.example.trip.dto.response.queryprojection.UserInfo;
import com.example.trip.redis.notification.Notification;
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
        private Long userId;
        private String userName;
        private String userProfileImg;
    }

    @AllArgsConstructor
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
        private String userProfileImg;
        private String userName;
        private Long userId;
    }

    @Builder
    @Getter
    public static class Responsev2 {
        private String result;
        private String msg;
        private UserInfo userInfo;
//        private List<Notification> notificationInfo;
        private List<FeedLocationResponseDto.BookMark> bookmark;

    }

    @AllArgsConstructor
    @Getter
    public static class LoginSuccess {
        private String result;
        private String msg;
        private boolean first;
        private TokenInfo tokens;
        private UserProfile userInfo;
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
    }
}
