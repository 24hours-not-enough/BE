package com.example.trip.dto.response;

import com.example.trip.domain.FeedComment;
import com.example.trip.dto.response.queryprojection.UserInfo;
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
//        private UserInfo creator;
        private String content;

        public GetComment(FeedComment feedComment) {
            this.commentId = feedComment.getId();
//            this.creator = new UserInfo(feedComment.getUser());
            this.content = feedComment.getContent();
        }
    }
}
