package com.example.trip.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatRoomService;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/{planId}")
    public void message(ChatSendDto message) {
        chatRoomService.handleMsg(message.getPlan_id(), message);

    }
}