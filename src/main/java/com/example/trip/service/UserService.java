package com.example.trip.service;

import com.example.trip.domain.User;
import com.example.trip.dto.response.UserResponseDto.UserBasic;
import com.example.trip.redis.notification.Notification;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    UserBasic findUser(Long userId);

    void registerLog(HttpServletRequest request, User user);

    List<Notification> getNotification(Long userId);
}
