package com.example.trip.redis.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class NotifyController {

    private final NotifyService notifyService;

    private final NotifyRepository notifyRepository;


    // 메세지가 왔을 때 -> app/notification/{userId}
    @MessageMapping("/notification/{userId}")
    public void notify(@DestinationVariable Long userId, NotifySendDto notification) {
        notifyService.handleMsg(userId, notification);
    }

    @GetMapping("/see/{userId}")
    public void show(@PathVariable Long userId) {
        List<Notification> check = notifyRepository.findNotificationsByUserId(userId);
        System.out.println("알림 개수 : " + check.size());
        check.stream().forEach((notification) -> {
            System.out.println(notification.getPlanId());
            System.out.println(notification.getPlanName());
        });
    }


}
