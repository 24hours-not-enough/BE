package com.example.trip.service.impl;

import com.example.trip.advice.exception.AlreadyExistUsernameException;
import com.example.trip.advice.exception.RefreshTokenNotFoundException;
import com.example.trip.advice.exception.UserNotFoundException;
import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.domain.Image;
import com.example.trip.domain.Role;
import com.example.trip.domain.User;
import com.example.trip.dto.request.TokenRequestDto;
import com.example.trip.dto.response.TokenResponseDto;
import com.example.trip.dto.response.UserResponseDto;
import com.example.trip.dto.response.UserResponseDto.GoogleUser;
import com.example.trip.dto.response.UserResponseDto.KakaoUser;
import com.example.trip.jwt.JwtTokenProvider;
import com.example.trip.repository.UserRepository;
import com.example.trip.service.SocialLoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocialLoginServiceImpl implements SocialLoginService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakao_client_id;

    @Value("${spring.security.oauth2.client.registration.kakao.secret-key}")
    private String kakao_client_secret;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String google_client_id;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String google_client_secret;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final S3UploaderServiceImpl s3UploaderService;
    private final RedisServiceImpl redisServiceImpl;



//    private static final Long AccessTokenValidTime = 1000000 * 60 * 1000L; // 1000000분(test)
    private static final Long AccessTokenValidTime = 10000000 * 60 * 1000L; // 1분(test)
    private static final Long RefreshTokenValidTime = 10080 * 60 * 1000L; // 일주일


    @Override
    @Transactional
    public UserResponseDto.KakaoLogin kakaoLogin(String code) throws JsonProcessingException {
        // 인가 코드로 액세스 토큰 요청
        String accessToken = getKakaoAccessToken(code);
        // 토큰으로 카카오 API 호출
        KakaoUser info = getKaKaoUserInfo(accessToken);

        // DB 에 중복된 Kakao Id 있는지 확인
        User user = userRepository.findBySocialaccountId(info.getKakaoId()).orElse(null);
        if (user == null) {
            // 회원가입
            user = kakaoRegister(info);
        }

        // 강제 로그인 처리
        UserDetails userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return UserResponseDto.KakaoLogin.builder()
                .kakaoId(info.getKakaoId())
                .email(info.getEmail())
                .user(user)
                .build();
    }

    @Override
    public String getKakaoAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakao_client_id);
        body.add("redirect_uri", "http://localhost:8081/api/kakaologin");
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

    @Override
    public KakaoUser getKaKaoUserInfo(String accessToken) throws JsonProcessingException {
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
        String kakaoId = "KAKAO_" + jsonNode.get("id").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();

        return KakaoUser.builder()
                .kakaoId(kakaoId)
                .email(email)
                .build();
    }


    @Override
    public User kakaoRegister(KakaoUser userInfo) {

        String password = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .email(userInfo.getEmail())
                .password(encodedPassword)
                .socialaccountId(userInfo.getKakaoId())
                .memberstatus(true)
                .image(new Image("imgUrl", "imgName"))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public UserResponseDto.GoogleLogin googleLogin(String code) throws JsonProcessingException {
        String accessToken = getGoogleAccessToken(code);
        GoogleUser info = getGoogleUserInfo(accessToken);
        User googleUser = userRepository.findBySocialaccountId(info.getGoogleId()).orElse(null);

        if (googleUser == null) {
            googleUser = googleRegister(info);
        }

        // 강제 로그인 처리
        UserDetails userDetails = new UserDetailsImpl(googleUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return UserResponseDto.GoogleLogin.builder()
                .googleId(info.getGoogleId())
                .email(info.getEmail())
                .user(googleUser)
                .build();
    }

    @Override
    public String getGoogleAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", google_client_id);
        body.add("client_secret", google_client_secret);
        body.add("redirect_uri", "http://localhost:8081/api/googlelogin");
        body.add("grant_type", "authorization_code");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<String> response = rt.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                googleTokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    @Override
    public GoogleUser getGoogleUserInfo(String accessToken) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        // HTTP Header 생성
        headers.add("Authorization", "Bearer " + accessToken);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<String> response = rt.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                googleUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String googleId = "GOOGLE_" + jsonNode.get("id").asText();
        String email = jsonNode.get("email").asText();
        return GoogleUser.builder()
                .googleId(googleId)
                .email(email)
                .build();
    }

    @Override
    public User googleRegister(GoogleUser googleUserInfo) {

        String password = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .email(googleUserInfo.getEmail())
                .password(encodedPassword)
                .socialaccountId(googleUserInfo.getGoogleId())
                .image(new Image("imgUrl", "imgName"))
                .memberstatus(true)
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return user;
    }

    @Override
    public UserResponseDto.TokenInfo issueToken(User user, HttpServletResponse response) {
        String accessToken = jwtTokenProvider.createToken(user.getSocialaccountId(), AccessTokenValidTime);
        String refreshToken = jwtTokenProvider.createToken(user.getSocialaccountId(), RefreshTokenValidTime);
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
        redisServiceImpl.setValues(refreshToken, user.getSocialaccountId());
        return UserResponseDto.TokenInfo.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();
    }

    @Override
    public void checkSameUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AlreadyExistUsernameException();
        }
    }

    @Override
    @Transactional
    public UserResponseDto.UserInfo registerProfile(String socialaccountId, String username, MultipartFile file) throws IOException {
        Optional<User> user = Optional.ofNullable(userRepository.findBySocialaccountId(socialaccountId)).orElseThrow(UserNotFoundException::new);
        if (file.isEmpty()) {
            user.get().update(username, "imgUrl", "imgName");
            return UserResponseDto.UserInfo.builder()
                    .userId(user.get().getId())
                    .userName(username)
                    .userProfileImg("imgUrl")
                    .build();
        } else {
            Map<String, String> imgUrl = s3UploaderService.upload(file);
            user.get().update(username, imgUrl.get(file.getOriginalFilename()), file.getOriginalFilename());
            return UserResponseDto.UserInfo.builder()
                    .userId(user.get().getId())
                    .userName(username)
                    .userProfileImg(imgUrl.get(file.getOriginalFilename()))
                    .build();
        }
    }

    @Override
    public boolean checkLoginFirst(User user) {
        if (user.getUsername() == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void checkUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AlreadyExistUsernameException();
        }
    }


    @Override
    public UserResponseDto.UserInfo sendUserInfo(User user) {
            return UserResponseDto.UserInfo.builder()
                    .userId(user.getId())
                    .userName(user.getUsername())
                    .userProfileImg(user.getImage().getFile_store_course())
                    .build();

    }

    @Override
    public UserResponseDto.UserInfo searchUser(String username) {
        Optional<User> byUsername = Optional.ofNullable(userRepository.findByUsername(username)).orElseThrow(UserNotFoundException::new);
        User user = byUsername.get();
        return UserResponseDto.UserInfo.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .userProfileImg(user.getImage().getFile_store_course())
                .build();
    }

    @Override
    @Transactional
    public void deleteAccount(String socialaccountId) {
        Optional<User> user = Optional.ofNullable(userRepository.findBySocialaccountId(socialaccountId)).orElseThrow(UserNotFoundException::new);
        user.get().deleteAccount();
    }

    @Override
    public User getUser(String socialaccountId) {
        Optional<User> user = Optional.ofNullable(userRepository.findBySocialaccountId(socialaccountId)).orElseThrow(UserNotFoundException::new);
        return user.get();
    }

    @Override
    @Transactional
    public TokenResponseDto reissueToken(TokenRequestDto requestDto) {
        String accessToken = requestDto.getAccessToken();
        String refreshToken = requestDto.getRefreshToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        Optional<User> bySocialaccountId = Optional.ofNullable(userRepository.findBySocialaccountId(authentication.getName())).orElseThrow(UserNotFoundException::new);
        if (redisServiceImpl.getValues(refreshToken) == null) { throw new RefreshTokenNotFoundException(); }
        redisServiceImpl.delValues(refreshToken);

        String snsId = bySocialaccountId.get().getSocialaccountId();
        String newAccessToken = jwtTokenProvider.createToken(snsId, AccessTokenValidTime);
        String newRefreshToken = jwtTokenProvider.createToken(snsId, RefreshTokenValidTime);
        redisServiceImpl.setValues(newRefreshToken, snsId);

        return new TokenResponseDto(newAccessToken, newRefreshToken);
    }
}

