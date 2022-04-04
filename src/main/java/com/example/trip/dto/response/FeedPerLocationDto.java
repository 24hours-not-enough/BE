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
    //피드별 이미지를 담은 리스트
    private List<ImagePerFeedDto> images;
    //좋아요를 한 유저의 정보를 담은 리스트
    private List<UserDataDto> likedUsers;
    //지역별 피드의 유저 정보
    private UserDataDto creator;
    private String memo;
    //피드별 댓글 정보를 담은 리스트
    private List<CommentPerFeedDto> comments;

    //피드별 피드 이미지를 매핑
    public void mapFeedImages(List<FeedDetailLocImg> feedDetailLocImgs) {
        images = new ArrayList<>();
        for (FeedDetailLocImg feedDetailLocImg : feedDetailLocImgs) {
            //Dto에 관련 정보를 담아주기
            ImagePerFeedDto imagePerFeedDto = ImagePerFeedDto.builder()
                    .imgUrl(feedDetailLocImg.getImgUrl())
                    .build();
            //매핑한 이미지를 리스트에 담기
            images.add(imagePerFeedDto);

        }

    }

    //피드별 좋아요를 Dto에 매핑
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

    //피드별 사용자 정보를 매핑
    public void mapFeedUser(User user) {
        this.creator = UserDataDto.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .userProfileImage(user.getImage().getFile_store_course())
                .build();
    }

    //피드별 댓글 정보를 DTO에 매핑
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

