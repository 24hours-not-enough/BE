package com.example.trip.feed;

import com.example.trip.domain.Feed;
import com.example.trip.domain.FeedDetail;
import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.FeedDetailLocImg;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FeedRequestDto {

    @Getter
    public static class FeedRequestRegisterDto{
        private String title;

//        @DateTimeFormat(pattern = "MM/dd/yyyy")
        private LocalDateTime travelStart;

//        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime travelEnd;

        private List<FeedDetail> feedDetail;
    }

    @Getter
    public static class FeedRequestModifyDto{
        private String title;

        private LocalDateTime travelStart;

        private LocalDateTime travelEnd;

        private List<FeedDetail> feedDetail;

    }

}
