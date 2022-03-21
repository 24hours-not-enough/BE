package com.example.trip.dto;

import com.example.trip.domain.FeedComment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FeedCommentResponseDto {
    private String username;
    private String content;

    @AllArgsConstructor
    @Getter
    public static class InFeed {
        private String username;
        private String content;

        public InFeed(FeedComment feedComment) {
            this.username = feedComment.getUser().getUsername();
            this.content = feedComment.getContent();
        }
    }
}
