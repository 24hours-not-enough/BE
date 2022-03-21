package com.example.trip.redis;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private Long room_id;
    private Long plan_id;

    public static ChatRoom create(Long plan_id) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.room_id = Long.parseLong(UUID.randomUUID().toString());
        chatRoom.plan_id = plan_id;
        return chatRoom;
    }
}
