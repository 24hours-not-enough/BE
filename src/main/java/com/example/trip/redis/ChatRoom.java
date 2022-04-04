package com.example.trip.redis;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {
    private Long room_id;
    private Long plan_id;

    public static ChatRoom create(Long plan_id) {
        ChatRoom chatRoom = new ChatRoom();
        // 방 번호도 계획 아이디와 같은 이유는 계획 하나당 방 번호가 하나씩만 배정되기 때문.
        chatRoom.room_id = plan_id;
        chatRoom.plan_id = plan_id;
        return chatRoom;
    }
}
