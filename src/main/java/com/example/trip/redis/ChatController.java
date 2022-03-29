package com.example.trip.redis;

import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatRoomService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat")
//    public void message(ChatSendDto message, @Header("token") String token) {
    public ResponseEntity<List<ChatMessage>> message(ChatSendDto message) {
//            System.out.println(token);
//        String nickName = jwtTokenProvider.getUserPk(token);
//        chatRoomService.handleMsg(message.getPlan_id(), message, nickName);
        List<ChatMessage> chatMessages = chatRoomService.handleMsg(message.getPlan_id(), message);
        chatMessages.forEach(x -> System.out.println(x.getChatMessage()));
        return new ResponseEntity<>(chatMessages, HttpStatus.OK);

    }
}