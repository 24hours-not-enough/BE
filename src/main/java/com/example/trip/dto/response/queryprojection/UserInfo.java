package com.example.trip.dto.response.queryprojection;

import com.example.trip.domain.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class UserInfo {
    private Long userId;
    private String userName;
    private String userProfileImage;

    @QueryProjection
    public UserInfo(User user) {
        this.userId = user.getId();
        this.userName = user.getUsername();
        this.userProfileImage = user.getImage().getFile_store_course();
    }
}
