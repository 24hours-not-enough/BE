package com.example.trip.service.impl;

import com.example.trip.advice.exception.UserNotFoundException;
import com.example.trip.domain.LoginLog;
import com.example.trip.domain.User;
import com.example.trip.dto.response.UserResponseDto;
import com.example.trip.repository.LoginLogRepository;
import com.example.trip.repository.UserRepository;
import com.example.trip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LoginLogRepository loginLogRepository;

    public UserResponseDto.GetUser findUser(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
        return new UserResponseDto.GetUser(user.get());
    }

    public void registerLog(HttpServletRequest request, UserResponseDto.KakaoLogin loginRequestDto) {
        String remoteAddr = request.getRemoteAddr();
        System.out.println(remoteAddr + "remoteAddr");
        Optional<User> user = userRepository.findBySocialaccountId(loginRequestDto.getKakaoId());
        LoginLog log = LoginLog.builder().email(loginRequestDto.getEmail()).login_ip(remoteAddr).user(user.get()).build();
        loginLogRepository.save(log);
    }
}
