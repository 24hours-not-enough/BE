package com.example.trip.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class ChatRepository {
    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis 해쉬 저장
    // 채팅 방
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private static final String CHAT_MESSAGES = "CHAT_MESSAGES";
    // Redis Template을 활용하여 Redis 서버에 Redis Command를 수행하기 위한 high-level 추상화를 제공
    private final RedisTemplate<String, Object> redisTemplate;
    //레디스 해쉬에 저장할떄 활용 : 채팅방
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    //레디스 해쉬에 저장할떄 활용 : 채팅 메세지
    private HashOperations<String, String, List<ChatMessage>> opsHashChatMsg;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 planId로 찾을 수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
        //레디스 템플릿은 재사용이 가능하다.
        opsHashChatMsg = redisTemplate.opsForHash();
    }


    // ** 채팅 메세지 관련 기능


    //각 채팅방마다 메세지를 조회
    public List<ChatMessage> findMsgById(Long planId) {
        return opsHashChatMsg.get(CHAT_MESSAGES, "CHAT" + planId.toString());
    }

    // 채팅방 메세지 생성 : 채팅방마다의 메세지를 redis hash에 저장한다.
    public void addChatMsg(Long planId, String msg) {
        //채팅 메세지를 빌더 패턴으로 만들고,
        ChatMessage chatMsg = ChatMessage.builder()
                .plan_id(planId)
                .chatMessage(msg)
                .build();

        // 메세지를 추가해줄 레포지토리를 찾는다.
        List<ChatMessage> chatMessages = opsHashChatMsg.get(CHAT_MESSAGES, "CHAT" + planId.toString());

        //메세지를 처음 보내는 경우에는 빈 리스트를 넣어줌
        if(chatMessages == null){
            chatMessages = new ArrayList<>();
            opsHashChatMsg.put(CHAT_MESSAGES, "CHAT" + planId.toString(), chatMessages);
        }
        // 해당 레디스 해쉬에 메세지 정보를 추가
        chatMessages.add(chatMsg);
        opsHashChatMsg.put(CHAT_MESSAGES, "CHAT" + planId.toString(), chatMessages);
    }





    // ** 채팅 방 관련 기능

    //채팅 방별로 정보를 조회
    public ChatRoom findRoomById(Long planId) {
        return opsHashChatRoom.get(CHAT_ROOMS, "CHAT"+planId.toString());
    }

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
    public void createChatRoom(Long planId) {
        //채팅방을 만들어서 레디스 해쉬에 저장한다.
        ChatRoom chatRoom = ChatRoom.create(planId);
        opsHashChatRoom.put(CHAT_ROOMS, "CHAT"+chatRoom.getPlan_id().toString(), chatRoom);
    }

    // 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
    public void enterChatRoom(Long planId) {
        ChannelTopic topic = topics.get("CHAT"+planId.toString());
        if (topic == null) {
            topic = new ChannelTopic("CHAT"+planId.toString());
            // 처음 토픽이 만들어지는 것이라면 리스너를 등록한다.
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            // 해당 토픽을 토픽 저장 레포지토리에 저장
            topics.put("CHAT"+planId, topic);
        }
    }

    public ChannelTopic getTopic(Long planId) {
        return topics.get("CHAT"+planId);
    }
}

