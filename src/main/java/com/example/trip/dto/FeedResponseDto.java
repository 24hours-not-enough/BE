package com.example.trip.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {
    private String result;
    private String msg;
    private Object data;

    @AllArgsConstructor
    @Getter
    public static class ReadOneTrip {
        private Long feedId;
        private String title;
        private LocalDateTime travelstart;
        private LocalDateTime travelend;
        private List<FeedDetailResponseDto> feeddetail;

    }

    @AllArgsConstructor
    @Getter
    public static class AllMyTrips {
        private String title;
        private LocalDateTime travelstart;
        private LocalDateTime travelend;
        private List<String> images;
    }
}
