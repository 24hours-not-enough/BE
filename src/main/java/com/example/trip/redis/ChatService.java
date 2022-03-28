package com.example.trip.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final RedisPublisher redisPublisher;
    private final ChatRepository chatRoomRepository;

    public void handleMsg(Long planId, ChatSendDto message, String nickName) {
        //채팅방으로 입장, 처음 생긴 방이라면 리스너 등록
        chatRoomRepository.enterChatRoom(planId);
        message.setMessage(nickName + "님이 입장하셨습니다.");
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        redisPublisher.publish(chatRoomRepository.getTopic(planId), message);
    }
}
