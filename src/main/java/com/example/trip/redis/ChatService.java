package com.example.trip.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final RedisPublisher redisPublisher;
    private final ChatRepository chatRoomRepository;

//    public void handleMsg(Long planId, ChatSendDto message, String nickName) {
public List<ChatMessage> handleMsg(Long planId, ChatSendDto message) {
        //기존의 존재하는 채팅방이 있는지 체크
        ChatRoom chatRoom = chatRoomRepository.findRoomById(planId);
        if (chatRoom == null){chatRoomRepository.createChatRoom(planId);}


//        ChatMessage chatMessages = chatRoomRepository.findMsgById(planId);
//        System.out.println("found msg" + "  " + chatMessages);
//        if (chatMessages == null){chatRoomRepository.createChatMsg(planId);}
        chatRoomRepository.addChatMsg(planId, message.getMessage());


        //채팅방으로 입장, 처음 생긴 방이라면 리스너 등록
        chatRoomRepository.enterChatRoom(planId);
//        message.setMessage(nickName + "님이 입장하셨습니다.");
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        redisPublisher.publish(chatRoomRepository.getTopic(planId), message);

        return chatRoomRepository.findMsgById(planId);
    }
}
