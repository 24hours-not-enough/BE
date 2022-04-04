package com.example.trip.domain;

import com.example.trip.dto.request.FeedRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedDetailTest {

    private Feed feed;

    @BeforeEach
    void setup() {
        String title = "제주도 여행";
        LocalDateTime travelStart = LocalDateTime.of(2020, 1, 23, 1, 33);
        LocalDateTime travelEnd = LocalDateTime.of(2021, 1, 23, 1, 33);
        List<FeedDetail> feedDetail = null;

        FeedRequestDto.FeedRequestRegisterDto dto = new FeedRequestDto.FeedRequestRegisterDto(
                title,
                travelStart,
                travelEnd,
                feedDetail
        );

        feed = Feed.builder()
                .title(dto.getTitle())
                .travelStart(dto.getTravelStart())
                .travelEnd(dto.getTravelEnd())
                .feedDetail(null)
                .build();
    }

    @Test
    @DisplayName("피드상세생성 - 정상케이스")
    void createFeedDetail_normal() {

        //given
        Feed feed1 = feed;
        String day = "1일차";

        //when
        FeedDetail feedDetail = FeedDetail.builder()
                .feed(feed1)
                .day(day)
                .build();

        //then
        assertNull(feedDetail.getId());
        assertEquals(feed1, feedDetail.getFeed());
        assertNull(feedDetail.getFeedDetailLoc());
        assertEquals(day, feedDetail.getDay());
    }
}