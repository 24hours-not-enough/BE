package com.example.trip.dto.response;

import com.example.trip.domain.FeedDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class FeedDetailResponseDto {

    @AllArgsConstructor
    @Getter
    public static class GetFeedDetail {
        private String day;
        private List<FeedDetailLocResponseDto.GetFeedDetailLoc> feedDetailLoc;

        public GetFeedDetail(FeedDetail feedDetail) {
            this.day = feedDetail.getDay();
            this.feedDetailLoc = feedDetail.getFeedDetailLoc().stream().map(FeedDetailLocResponseDto.GetFeedDetailLoc::new).collect(Collectors.toList());
        }
    }

    @AllArgsConstructor
    @Getter
    public static class GetOnlyLoc {
        private List<FeedDetailLocResponseDto.GetOnlyImg> day;
        public GetOnlyLoc(FeedDetail feedDetail) {
            this.day = feedDetail.getFeedDetailLoc().stream().map(FeedDetailLocResponseDto.GetOnlyImg::new).collect(Collectors.toList());
        }
    }
}
