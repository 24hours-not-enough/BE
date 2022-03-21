package com.example.trip.dto;

import com.example.trip.domain.FeedDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class FeedDetailResponseDto {

    @AllArgsConstructor
    @Getter
    public static class ReadDetail {
        private Long feeddetailId;
        private String day;
        private List<FeedDetailLocResponseDto.ReadDetailLoc> feeddetailloc;

        public ReadDetail(FeedDetail feedDetail) {
            this.feeddetailId = feedDetail.getId();
            this.day = feedDetail.getDay();
            this.feeddetailloc = feedDetail.getFeedDetailLoc().stream()
                    .map(FeedDetailLocResponseDto.ReadDetailLoc::new)
                    .collect(Collectors.toList());
        }
    }
}
