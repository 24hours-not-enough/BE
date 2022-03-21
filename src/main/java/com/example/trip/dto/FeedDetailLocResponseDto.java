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
    public static class ReadDetailLoc {
        private String location;
        private String city;
        private String content;
        private List<String> images;

        public ReadDetailLoc(FeedDetailLoc feedDetailLoc) {
            this.location = feedDetailLoc.getLocation();
            this.city = feedDetailLoc.getCity();
            this.content = feedDetailLoc.getComment();
            this.images = feedDetailLoc.getFeedDetailLocImg().stream().map(x -> x.getImgUrl()).collect(Collectors.toList());
        }

    }

    @AllArgsConstructor
    @Getter
    public static class ReadOneFeed {
        private String location;
        private String city;
        private String content;
        private List<FeedDetailLocImgResponseDto.ImgUrl> images;
        private List<FeedCommentResponseDto.InFeed> comments;

        public ReadOneFeed(FeedDetailLoc feedDetailLoc) {
            this.location = feedDetailLoc.getLocation();
            this.city = feedDetailLoc.getCity();
            this.content = feedDetailLoc.getComment();
            this.images = feedDetailLoc.getFeedDetailLocImg().stream().map(FeedDetailLocImgResponseDto.ImgUrl::new).collect(Collectors.toList());
            this.comments = feedDetailLoc.getFeedComments().stream().map(FeedCommentResponseDto.InFeed::new).collect(Collectors.toList());
        }
    }

}
