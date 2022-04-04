package com.example.trip.dto.response.queryprojection;

import com.example.trip.domain.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class UserInfo {
    private Long userId;
    private String userName;
    private String userProfileImage;
//    private String password;
//    private boolean memberstatus;
//    private String socialaccountId;
//    private Role role;
//    private Image image;

    @QueryProjection
    public UserInfo(User user) {
        this.userId = user.getId();
        this.userName = user.getUsername();
        this.userProfileImage = user.getImage().getFile_store_course();
//        this.password = user.getPassword();
//        this.memberstatus = user.isMemberstatus();
//        this.socialaccountId = user.getSocialaccountId();
//        this.role = user.getRole();
//        this.image = user.getImage();
    }

    public UserInfo(Long userId, String username, String userProfileImage) {
        this.userId = userId;
        this.userName = username;
        this.userProfileImage = userProfileImage;
    }
}
