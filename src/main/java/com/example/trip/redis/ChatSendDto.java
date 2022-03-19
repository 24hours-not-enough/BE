package com.example.trip.redis;

import lombok.*;

@Getter
// 빌더가 있는데도 사용하는 이유는, STOMP에서 Dto에 값을 넣어줄 때 set으로 넣어주기때문.
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSendDto {
    //채팅 메세지에 대한 정보와, 채팅 방에 대한 정보가 담겨있다.
    private String message;
    private Long user_id;
    private Long room_id;
    private Long plan_id;
}
