package com.example.trip.service;

import com.example.trip.domain.User;
import com.example.trip.dto.response.UserResponseDto;
import com.example.trip.dto.response.queryprojection.UserInfo;
import com.example.trip.redis.notification.Notification;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    UserInfo findUser(Long userId);

    void registerLog(HttpServletRequest request, User user);

    List<Notification> getNotification(Long userId);
}
