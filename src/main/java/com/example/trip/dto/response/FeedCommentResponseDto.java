package com.example.trip.dto.response;

import com.example.trip.domain.FeedComment;
import com.example.trip.dto.response.UserResponseDto.UserBasic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class FeedCommentResponseDto {
    private String username;
    private String content;

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class GetComment {

        private Long commentId;
        private UserBasic creator;
        private String content;

        public GetComment(FeedComment feedComment) {
            this.commentId = feedComment.getId();
            this.creator = new UserBasic(feedComment.getUser());
            this.content = feedComment.getContent();
        }
    }
}
