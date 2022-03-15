package com.example.trip.feed;

import com.example.trip.domain.Feed;
import com.example.trip.domain.FeedDetail;
import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.FeedDetailLocImg;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class FeedRequestDto {

    @Getter
    public static class FeedRequestRegisterDto{
        private String title;
        private List<FeedDetail> feedDetail;
    }

    @Getter
    public static class FeedRequestModifyDto{
        private Long feedId;
    }

}
