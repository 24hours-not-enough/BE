package com.example.trip.dto.response;

import com.example.trip.domain.Likes;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
    @Getter
    public static class GetUserId {
        private Long userId;

        public GetUserId(Likes likes) {
            this.userId = likes.getUser().getId();
        }
    }
}
