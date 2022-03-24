package com.example.trip.jwt;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final Long AccessTokenValidTime = 30 * 60 * 1000L; // 30분

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        // 유효한 토큰인지 확인합니다.
        if (accessToken != null) {
            // 어세스 토큰이 유효한 상황
            if (jwtTokenProvider.validateToken(accessToken)) {
                this.setAuthentication(accessToken);
            }
            // 어세스 토큰이 만료된 상황 | 리프레시 토큰 또한 존재하는 상황
            else if (!jwtTokenProvider.validateToken(accessToken) && refreshToken != null) {
                // 재발급 후, 컨텍스트에 다시 넣기
                /// 리프레시 토큰 검증
                boolean validateRefreshToken = jwtTokenProvider.validateToken(refreshToken);
                /// 리프레시 토큰 저장소 존재유무 확인
                boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);
                if (validateRefreshToken && isRefreshToken) {
                    /// 리프레시 토큰으로 sns계정 아이디 정보 가져오기
                    String snsaccountId = jwtTokenProvider.getUserPk(refreshToken);
                    /// 토큰 발급
                    String newAccessToken = jwtTokenProvider.createToken(snsaccountId, AccessTokenValidTime);
                    /// 헤더에 어세스 토큰 추가
                    jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                    /// 컨텍스트에 넣기
                    this.setAuthentication(newAccessToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    // SecurityContext 에 Authentication 객체를 저장합니다.
    public void setAuthentication(String token) {
        // 토큰으로부터 유저 정보를 받아옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        // SecurityContext 에 Authentication 객체를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
