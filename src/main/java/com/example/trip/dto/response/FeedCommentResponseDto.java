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
    public static class GetComment {

        private Long commentId;
        private String username;
        private String content;

        public GetComment(FeedComment feedComment) {
            this.commentId = feedComment.getId();
            this.username = feedComment.getUser().getUsername();
            this.content = feedComment.getContent();
        }
    }
}
