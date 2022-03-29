package com.example.trip.redis;

import com.example.trip.jwt.JwtAuthenticationFilter;
import com.example.trip.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class ChatRoomController {
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUser(HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
/// 리프레시 토큰으로 sns계정 아이디 정보 가져오기
        String snsaccountId = jwtTokenProvider.getUserPk(refreshToken);
        /// 토큰 발급
        String newAccessToken = jwtTokenProvider.createToken(snsaccountId, 30 * 60 * 1000L);

        String name = auth.getName();
        return LoginInfo.builder().name(name).token(newAccessToken).build();
    }
}
