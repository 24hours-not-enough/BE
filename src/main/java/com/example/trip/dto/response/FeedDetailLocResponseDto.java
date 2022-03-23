package com.example.trip.dto;


import com.example.trip.domain.FeedDetailLoc;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Getter
public class FeedDetailLocResponseDto {

    @AllArgsConstructor
    @Getter
    public static class GetFeedDetailLoc {
        private Long feedDetailLocId;
        //        private List<FeedDetailLocImgResponseDto.ImgUrl> images;
        private List<String> images;
        private List<LikesResponseDto.GetUserId> like;
        private UserResponseDto.GetUser creator;
        private String content;
        private List<FeedCommentResponseDto.GetComment> comments;

        public GetFeedDetailLoc(FeedDetailLoc feedDetailLoc) {
            this.feedDetailLocId = feedDetailLoc.getId();
            this.images = feedDetailLoc.getFeedDetailLocImg().stream().map(x -> x.getImgUrl()).collect(Collectors.toList());
//            this.images = feedDetailLoc.getFeedDetailLocImg().stream().map(FeedDetailLocImgResponseDto.ImgUrl::new).collect(Collectors.toList());
            this.like = feedDetailLoc.getLikes().stream().map(LikesResponseDto.GetUserId::new).collect(Collectors.toList());
            this.creator = new UserResponseDto.GetUser(feedDetailLoc.getFeedDetail().getFeed().getUser());
            this.content = feedDetailLoc.getMemo();
            this.comments = feedDetailLoc.getFeedComments().stream().map(FeedCommentResponseDto.GetComment::new).collect(Collectors.toList());
        }
}
}
