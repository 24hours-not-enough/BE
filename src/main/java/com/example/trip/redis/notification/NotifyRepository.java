package com.example.trip.redis.notification;

import com.example.trip.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class NotifyRepository {

    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final NotifySubscriber notifySubscriber;
    // Redis
    private static final String NOTIFICATION = "NOTIFICATION";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, List<Notification>> opsHashNotification;
    private Map<String, ChannelTopic> topics;

    private final RedisPublisher redisPublisher;

    @PostConstruct
    private void init() {
        opsHashNotification = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<List<Notification>> findAllNotifications() {
        return opsHashNotification.values(NOTIFICATION);
    }

    // 키값으로 벨류 가져오기
    public List<Notification> findNotificationsByUserId(Long userId){
        return opsHashNotification.get(NOTIFICATION, "USER" + userId);
    }

    public void createNotification(Long userId, NotifySendDto dto) {
        Notification notification = new Notification(dto);
        if (opsHashNotification.get(NOTIFICATION, "USER" + userId) == null) {
            ArrayList<Notification> empty = new ArrayList<>();
            empty.add(notification);
            opsHashNotification.put(NOTIFICATION, "USER" + userId, empty);
        } else {
            List<Notification> current = opsHashNotification.get(NOTIFICATION, "USER" + userId);
            current.add(notification);
            opsHashNotification.put(NOTIFICATION, "USER" + userId, current);
        }
    }

    // 메세지가 도달해야 하는 userId의 queue에 입장
    public void enterNotification(Long userId) {
        // 해당 userId를 key값으로 가지고 있는 ChannelTopic을 찾음
        ChannelTopic topic = topics.get("USER" + userId);
        // 만일 그 topic 값이 없다면,
        if (topic == null) {
            // 새롭게 ChannelTopic을 하나 만들어서,
            topic = new ChannelTopic(userId.toString());
            // /queue/notifySendDto.getTo_user_id() 로 연결
            redisMessageListener.addMessageListener(notifySubscriber, topic);
            topics.put("USER" + userId, topic);
        }
    }

    public ChannelTopic getTopic(Long userId) {
        return topics.get("USER" + userId);
    }
}
