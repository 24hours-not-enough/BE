package com.example.trip.redis;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements Serializable {
//    private Long user_id;
    private Long plan_id;
    private String chatMessage;

//    public ChatMessage create(Long plan_id, String msg) {
////        ChatMessage chatMessage = new ChatMessage();
////        chatMessage.user_id = user_id;
//        this.plan_id = plan_id;
//        this.chatMessage.add(msg);
//        return this;
//    }
}
