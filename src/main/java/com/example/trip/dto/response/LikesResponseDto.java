package com.example.trip.dto.response;

import com.example.trip.domain.Likes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class LikesResponseDto {

    @AllArgsConstructor
    @Getter
    public static class SortByCity {
        private String city;
        private int totalfeed;
        private List<String> images;
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class GetUserId {
        private Long userId;

        public GetUserId(Likes likes) {
            this.userId = likes.getUser().getId();
        }
    }
}
