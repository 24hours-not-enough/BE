package com.example.trip.service;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.domain.Role;
import com.example.trip.domain.User;
import com.example.trip.dto.*;
import com.example.trip.jwt.JwtTokenProvider;
import com.example.trip.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    String kakao_client_id = System.getenv("kakao_client_id");
    String kakao_client_secret = System.getenv("kakao_client_secret");
    String google_client_id = System.getenv("google_client_id");
    String google_client_secret = System.getenv("google_client_secret");

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private static final Long AccessTokenValidTime = 30 * 60 * 1000L; // 30분
    private static final Long RefreshTokenValidTime = 10080 * 60 * 1000L; // 일주일

    @Transactional
    public KakaoLoginRequestDto kakaoLogin(String code, SignupRequestDto signupRequestDto) throws JsonProcessingException {
        // 인가 코드로 액세스 토큰 요청
        String accessToken = getKakaoAccessToken(code);
        // 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKaKaoUserInfo(accessToken);

        // DB 에 중복된 Kakao Id 있는지 확인
        Long kakaoId = kakaoUserInfo.getKakaoId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {
            // 회원가입
            kakaoRegister(kakaoUserInfo, signupRequestDto);
        }

        // 강제 로그인 처리
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new KakaoLoginRequestDto(kakaoUserInfo.getEmail(), kakaoId);

    }

    private String getKakaoAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakao_client_id);
        body.add("redirect_uri", "http://localhost:80/api/kakaologin");
        body.add("code", code);
        body.add("client_secret", kakao_client_secret);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        // JSON 형식을 JAVA에서 사용하기 위해 objectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKaKaoUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        // HTTP Header 생성
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();


        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        System.out.println("카카오 사용자 정보: " + "ID: " + id + "이메일: " + email);
        return new KakaoUserInfoDto(id, email);
    }

    private void kakaoRegister(KakaoUserInfoDto kakaoUserInfo, SignupRequestDto signupRequestDto) {
        String email = kakaoUserInfo.getEmail();
        Long kakaoId = kakaoUserInfo.getKakaoId();

        String password = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(password);

        Role role = Role.USER;
        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .kakaoId(kakaoId)
                .memberstatus(true)
                .role(role)
                .username(signupRequestDto.getUsername())
                .image(signupRequestDto.getImage())
                .build();
        userRepository.save(user);
    }

    public GoogleLoginRequestDto googleLogin(String code, SignupRequestDto requestDto) throws JsonProcessingException {
        String accessToken = getGoogleAccessToken(code);
        GoogleUserInfoDto googleUserInfo = getGoogleUserInfo(accessToken);
        String googleId = googleUserInfo.getGoogleId();
        User googleUser = userRepository.findByGoogleId(googleId)
                .orElse(null);
        if (googleUser == null) {
            // 회원가입
            googleRegister(googleUserInfo, requestDto);
        }

        // 강제 로그인 처리
        UserDetails userDetails = new UserDetailsImpl(googleUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new GoogleLoginRequestDto(googleUserInfo.getEmail(), googleId);
    }

    private String getGoogleAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", google_client_id);
        body.add("client_secret", google_client_secret);
        body.add("redirect_uri", "http://localhost:80/api/googlelogin");
        body.add("grant_type", "authorization_code");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                googleTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        // JSON 형식을 JAVA에서 사용하기 위해 objectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private GoogleUserInfoDto getGoogleUserInfo(String accessToken) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        // HTTP Header 생성
        headers.add("Authorization", "Bearer " + accessToken);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                googleUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        System.out.println(responseBody);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String googleId = jsonNode.get("id").asText();
        String email = jsonNode.get("email").asText();
        System.out.println("구글 사용자 정보: " + "ID: " + googleId + " 이메일: " + email);
        return new GoogleUserInfoDto(googleId, email);
    }

    private void googleRegister(GoogleUserInfoDto googleUserInfo, SignupRequestDto requestDto) {
        String email = googleUserInfo.getEmail();
        String googleId = googleUserInfo.getGoogleId();

        String password = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(password);

        Role role = Role.USER;
        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .googleId(googleId)
                .role(role)
                .username(requestDto.getUsername())
                .image(requestDto.getImage())
                .build();
        userRepository.save(user);
    }

    public LoginResponseDto issueKakaoJwtToken(KakaoLoginRequestDto loginRequestDto) {
        String accessToken = jwtTokenProvider.createToken(loginRequestDto.getEmail(), AccessTokenValidTime);
        String refreshToken = jwtTokenProvider.createToken(loginRequestDto.getEmail(), RefreshTokenValidTime);
        userRepository.updateRefreshToken(loginRequestDto.getEmail(), refreshToken);
        return new LoginResponseDto(accessToken, refreshToken);
    }

    public LoginResponseDto issueGoogleJwtToken(GoogleLoginRequestDto loginRequestDto) {
        String accessToken = jwtTokenProvider.createToken(loginRequestDto.getEmail(), AccessTokenValidTime);
        String refreshToken = jwtTokenProvider.createToken(loginRequestDto.getEmail(), RefreshTokenValidTime);
        userRepository.updateRefreshToken(loginRequestDto.getEmail(), refreshToken);
        return new LoginResponseDto(accessToken, refreshToken);
    }
}
