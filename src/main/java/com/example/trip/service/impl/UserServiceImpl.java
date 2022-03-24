package com.example.trip.service.impl;

import com.example.trip.advice.exception.UserNotFoundException;
import com.example.trip.domain.User;
import com.example.trip.dto.response.UserResponseDto;
import com.example.trip.repository.UserRepository;
import com.example.trip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserResponseDto.GetUser findUser(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
        return new UserResponseDto.GetUser(user.get());
    }



}
