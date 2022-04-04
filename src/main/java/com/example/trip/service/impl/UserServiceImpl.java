package com.example.trip.service.impl;

import com.example.trip.advice.exception.UserNotFoundException;
import com.example.trip.domain.LoginLog;
import com.example.trip.domain.User;
import com.example.trip.dto.response.UserResponseDto;
import com.example.trip.dto.response.queryprojection.UserInfo;
import com.example.trip.redis.notification.Notification;
import com.example.trip.repository.LoginLogRepository;
import com.example.trip.repository.UserRepository;
import com.example.trip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LoginLogRepository loginLogRepository;

    private static final String NOTIFICATION = "NOTIFICATION";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, List<Notification>> opsHashNotification;

    public UserInfo findUser(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
        return new UserInfo(user.get());
    }

    public void registerLog(HttpServletRequest request, UserResponseDto.KakaoLogin loginRequestDto) {
        String remoteAddr = request.getRemoteAddr();
        Optional<User> user = Optional.ofNullable(userRepository.findBySocialaccountId(loginRequestDto.getKakaoId())).orElseThrow(UserNotFoundException::new);
        LoginLog log = LoginLog.builder()
                .email(loginRequestDto.getEmail())
                .login_ip(remoteAddr)
                .user(user.get()).build();
        loginLogRepository.save(log);
    }

    public List<Notification> getNotification(Long userId) {
        opsHashNotification = redisTemplate.opsForHash();
        return opsHashNotification.get(NOTIFICATION, "USER" + userId);
    }
}
