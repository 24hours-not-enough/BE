package com.example.trip.redis.notification;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifySendDto {

    private Long planId;
    private String planName;
    private Long feedDetailLocId;
    private Long feedDetailLocName;
    private String sendUserName;
    private String sendUserProfileImg;
    private String category;
    private Long receiveUserId;
}
