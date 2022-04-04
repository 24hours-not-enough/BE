package com.example.trip.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FeedDetailLocTest {

    FeedDetail feedDetail;
    FeedLocation feedLocation;

    @BeforeEach
    void setup() {
        Feed feed = Feed.builder()
                .title("제주도 여행")
                .travelStart(LocalDateTime.of(2020, 1, 23, 1, 33))
                .travelEnd(LocalDateTime.of(2021, 1, 23, 1, 33))
                .feedDetail(null)
                .build();


        feedDetail = FeedDetail.builder()
                .feed(feed)
                .feedDetailLoc(null)
                .day("1일차")
                .build();

        feedLocation = FeedLocation.builder()
                .name("감귤농장")
                .latitude(1006L)
                .longitude(1054L)
                .placeAddress("서귀포시")
                .build();

    }

    @Test
    @DisplayName("피드상세위치생성 - 정상케이스")
    void createFeedDetailLoc_normal() {

        // given
        String memo = "날씨가 좋아서 더 좋았던 제주도";
        FeedDetail detail = feedDetail;
        FeedLocation location = feedLocation;

        // when
        FeedDetailLoc feedDetailLoc = FeedDetailLoc.builder()
                .memo(memo)
                .feedDetail(detail)
                .feedLocation(location)
                .build();

        // then
        assertNull(feedDetailLoc.getId());
        assertNull(feedDetailLoc.getFeedDetailLocImg());
        assertNull(feedDetailLoc.getLikes());
        assertNull(feedDetailLoc.getFeedComments());
        assertEquals(memo, feedDetailLoc.getMemo());
        assertEquals(detail, feedDetailLoc.getFeedDetail());
        assertEquals(location, feedDetailLoc.getFeedLocation());
    }
}