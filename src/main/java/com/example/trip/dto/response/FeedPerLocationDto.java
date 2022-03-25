package com.example.trip.dto.response;

import com.example.trip.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class FeedPerLocationDto {
    private Long feedId;
    private LocalDateTime date;
    private List<ImagePerFeedDto> images;
    private List<UserDataDto> likedUsers;
    private UserDataDto creator;
    private String memo;
    private List<CommentPerFeedDto> comments;

    public void mapFeedImages(List<FeedDetailLocImg> feedDetailLocImgs) {
        images = new ArrayList<>();
        for (FeedDetailLocImg feedDetailLocImg : feedDetailLocImgs) {
            ImagePerFeedDto imagePerFeedDto = ImagePerFeedDto.builder()
                    .imgUrl(feedDetailLocImg.getImgUrl())
                    .build();
            images.add(imagePerFeedDto);

        }

    }

    public void mapFeedLikes(List<Likes> likes) {
        likedUsers = new ArrayList<>();
        for (Likes likedUser : likes) {
            UserDataDto likedUserData = UserDataDto.builder()
                    .userId(likedUser.getUser().getId())
                    .userName(likedUser.getUser().getUsername())
                    .userProfileImage(likedUser.getUser().getImage().getFile_store_course())
                    .build();
            likedUsers.add(likedUserData);

        }

    }

    public void mapFeedUser(User user) {
        this.creator = UserDataDto.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .userProfileImage(user.getImage().getFile_store_course())
                .build();
    }

    public void mapFeedComment(List<FeedComment> feedComments) {
        comments = new ArrayList<>();
        for (FeedComment feedComment : feedComments) {
            CommentPerFeedDto commentPerFeedDto = CommentPerFeedDto.builder()
                    .id(feedComment.getId())
                    .content(feedComment.getContent())
                    .build();
            commentPerFeedDto.updateUser(feedComment.getUser());
            comments.add(commentPerFeedDto);

        }

    }

}
