package com.example.trip.redis.notification;

import com.example.trip.redis.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotifyService {

    private final NotifyRepository notifyRepository;
    private final RedisPublisher redisPublisher;

    public void handleMsg(Long userId, NotifySendDto notification) {
        notifyRepository.enterNotification(userId);
        notifyRepository.createNotification(userId, notification);
        redisPublisher.publicMsg(notifyRepository.getTopic(userId), notification);
    }
}
