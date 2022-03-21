package com.example.trip.jwt;

import com.example.trip.exceptionhandling.CustomException;
import com.example.trip.exceptionhandling.ErrorCode;
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
//    private static final Long AccessTokenValidTime = 10 * 1000L; // 10초(테스트)

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        // 유효한 토큰인지 확인합니다.
        if (accessToken != null) {
            // 어세스 토큰이 유효한 상황
            if (jwtTokenProvider.validateToken(accessToken)) {
                System.out.println("access token 만료되지 않았음");
                this.setAuthentication(accessToken);
            }
            // 어세스 토큰이 만료된 상황 | 리프레시 토큰 또한 존재하는 상황
            else if (!jwtTokenProvider.validateToken(accessToken) && refreshToken != null) {
                System.out.println("access token 만료됨");
                // 재발급 후, 컨텍스트에 다시 넣기
                /// 리프레시 토큰 검증
                boolean validateRefreshToken = jwtTokenProvider.validateToken(refreshToken);
                /// 리프레시 토큰 저장소 존재유무 확인
                boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);
                if (validateRefreshToken && isRefreshToken) {
                    /// 리프레시 토큰으로 sns계정 아이디 정보 가져오기
                    String snsaccountId = jwtTokenProvider.getUserPk(refreshToken);
                    /// 이메일로 권한정보 받아오기
//                    List<String> roles = jwtTokenProvider.getRoles(email);
                    /// 토큰 발급
                    String newAccessToken = jwtTokenProvider.createToken(snsaccountId, AccessTokenValidTime);
                    /// 헤더에 어세스 토큰 추가
                    jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                    /// 컨텍스트에 넣기
                    this.setAuthentication(newAccessToken);
                }
            }
//            else if (refreshToken == null) {
//                jwtTokenProvider.notExistRefreshToken();
//            }
//            filterChain.doFilter(request, response);
//        } else {
//            jwtTokenProvider.notExistAccessToken();
//            filterChain.doFilter(request, response);
//        }
            // access token이 null만 아니면 통과되는 문제 해결 ! -> jwt exception 처리 -> 확인필요
            // refresh token 유효하지 않으면 다시 40번째 줄에서 에러 -> 확인필요
            // refresh token이 null이면? -> 재로그인이 필요합니다. 다시 접속하여 주세요. -> notExistRefreshToken -> 확인필요
            // access token이 null이면 예외처리 -> 로그인이 필요한 기능입니다. -> not Exist Access Token -> 확인필요
            // -> 스프링 시큐리티에서 잡아주는 건가


        }
        filterChain.doFilter(request, response);
    }

    // SecurityContext 에 Authentication 객체를 저장합니다.
    public void setAuthentication(String token) {
        // 토큰으로부터 유저 정보를 받아옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        // SecurityContext 에 Authentication 객체를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        // 유효한 토큰인지 확인
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            // SecurityContext 에 Authentication 객체를 저장
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        chain.doFilter(request, response);
    }
}
