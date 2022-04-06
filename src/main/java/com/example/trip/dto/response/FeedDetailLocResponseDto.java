package com.example.trip.dto.response;


import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.User;
import com.example.trip.dto.response.queryprojection.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Getter
public class FeedDetailLocResponseDto {

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class GetFeedDetailLoc {
        private Long feedDetailLocId;
        private LocalDateTime date;
        private String title;
        private List<FeedDetailLocImgResponseDto.ImgUrl> images;
        private List<LikesResponseDto.GetUserId> like;
        private UserInfo creator;
        private String content;
        private List<FeedCommentResponseDto.GetComment> comments;


        public GetFeedDetailLoc(FeedDetailLoc feedDetailLoc) {
            this.feedDetailLocId = feedDetailLoc.getId();
            this.date = feedDetailLoc.getCreatedAt();
            this.title = feedDetailLoc.getFeedLocation().getName();
            this.images = feedDetailLoc.getFeedDetailLocImg().stream().map(FeedDetailLocImgResponseDto.ImgUrl::new).collect(Collectors.toList());
            this.like = feedDetailLoc.getLikes().stream().map(LikesResponseDto.GetUserId::new).collect(Collectors.toList());
            this.creator = new UserInfo(feedDetailLoc.getFeedDetail().getFeed().getUser());
            this.content = feedDetailLoc.getMemo();
            this.comments = feedDetailLoc.getFeedComments().stream().map(FeedCommentResponseDto.GetComment::new).collect(Collectors.toList());
        }
}

        public static class GetOnlyImg {
            private List<String> images;

            public GetOnlyImg(FeedDetailLoc feedDetailLoc) {
                this.images = feedDetailLoc.getFeedDetailLocImg().stream().map(x -> x.getImgUrl()).collect(Collectors.toList());
            }
        }
}
