package com.example.trip.redis;

import com.example.trip.redis.notification.NotifySendDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String,Object> redisTemplate;

    //채팅 메세지를 pub할 때 사용
    public void publish(ChannelTopic topic, ChatSendDto chatSendDto){
        //Dto를 해당 토픽으로 보내기
        System.out.println(topic.getTopic());
        System.out.println(chatSendDto.getMessage());
        redisTemplate.convertAndSend(topic.getTopic(),chatSendDto);
    }

    //알림을 publish할 때 사용
    public void publicMsg(ChannelTopic topic, NotifySendDto dto) {
        redisTemplate.convertAndSend(topic.getTopic(), dto);
    }
}

