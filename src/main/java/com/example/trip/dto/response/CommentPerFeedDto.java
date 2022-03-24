package com.example.trip.dto.response;

import com.example.trip.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class CommentPerFeedDto {

    private Long id;

    private UserDataDto creator;

    private String content;

    public void updateUser(User user){
        this.creator = UserDataDto.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .userProfileImage(user.getImage().getFile_store_course())
                .build();
    }

}
