package com.example.trip.dto.response;

import com.example.trip.domain.Feed;
import com.example.trip.dto.response.FeedDetailLocResponseDto.GetFeedDetailLoc;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class FeedResponseDto {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class FeedResponseDefault{
        private String result;
        private String msg;
    }
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class FeedResponseOptional{
        private String result;
        private String msg;
        private Object data;
    }

    @AllArgsConstructor
    @Getter
    public static class GetFeed {
        private Long feedId;
        private String title;
        private LocalDateTime travelStart;
        private LocalDateTime travelEnd;
        private List<FeedDetailResponseDto.GetFeedDetail> feedDetail;
        private List<String> images;

        public GetFeed(Feed feed) {

            images = new ArrayList<>();

            this.feedId = feed.getId();
            this.title = feed.getTitle();
            this.travelStart = feed.getTravelStart();
            this.travelEnd = feed.getTravelEnd();
            this.feedDetail = feed.getFeedDetail().stream().map(FeedDetailResponseDto.GetFeedDetail::new).collect(Collectors.toList());
            feed.getFeedDetail().forEach(e -> e.getFeedDetailLoc().forEach(x -> x.getFeedDetailLocImg().forEach(y -> images.add(y.getImgUrl()))));
        }
    }

    @Builder
    @Getter
    public static class Response {
        private String result;
        private String msg;
        private List<FeedResponseDto.GetFeed> myFeeds;
        private List<Map<String, List<GetFeedDetailLoc>>> myLikes;
    }


}
