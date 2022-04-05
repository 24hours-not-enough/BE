package com.example.trip.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    //역직렬화된 메세지를 읽어와서 DTO에 바인딩 해줄 때 사용
    private final ObjectMapper objectMapper;
    //직혈화된 것을 역직렬화할 때 사용
    private final RedisTemplate redisTemplate;
    //해당하는 방으로 메세지를 보내는 역할
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            // 메세지를 역직렬화
            String publicshMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            //역직렬화한 메세지를 Dto형태로 가공
            ChatSendDto chatSendDto = objectMapper.readValue(publicshMessage,ChatSendDto.class);
            //해당하는 방으로 메세지를 보냄
            messagingTemplate.convertAndSend("/topic/"+chatSendDto.getPlan_id(), chatSendDto);

        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
}


