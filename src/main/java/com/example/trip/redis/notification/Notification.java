package com.example.trip.redis.notification;

import lombok.Getter;

import java.io.Serializable;


@Getter
public class Notification implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String category;
    private Long planId;
    private String planName;
    private Long feedDetailLocId;
    private Long feedDetailLocName;
    private String sendUserName;
    private String sendUserProfileImg;
    private Long receiveUserId;

    public Notification(NotifySendDto dto) {
        this.category = dto.getCategory();
        this.planId = dto.getPlanId();
        this.planName = dto.getPlanName();
        this.feedDetailLocId = dto.getFeedDetailLocId();
        this.feedDetailLocName = dto.getFeedDetailLocName();
        this.sendUserName = dto.getSendUserName();
        this.sendUserProfileImg = dto.getSendUserProfileImg();
        this.receiveUserId = dto.getReceiveUserId();
    }
}
