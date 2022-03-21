package com.example.trip.dto;

import com.example.trip.domain.Feed;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedResponseDefault{
        private String result;
        private String msg;
    }
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedResponseOptional{
        private String result;
        private String msg;
        private Object data;
    }


    @AllArgsConstructor
    @Getter
    public static class ReadOneTrip {
        private Long feedId;
        private String title;
        private LocalDateTime travelstart;
        private LocalDateTime travelend;
        private List<FeedDetailResponseDto.ReadDetail> feeddetail;

        public ReadOneTrip(Feed feed) {
            this.feedId = feed.getId();
            this.title = feed.getTitle();
            this.travelstart = feed.getTravelStart();
            this.travelend = feed.getTravelEnd();
            this.feeddetail = feed.getFeedDetail().stream()
                    .map(FeedDetailResponseDto.ReadDetail::new).collect(Collectors.toList());
        }
    }

    @AllArgsConstructor
    @Getter
    public static class AllMyTrips {
        private String title;
        private LocalDateTime travelstart;
        private LocalDateTime travelend;
        private int imgcnt;
        private List<String> imgs;
    }
}
