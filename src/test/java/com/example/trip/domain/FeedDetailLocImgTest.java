package com.example.trip.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FeedDetailLocImgTest {

    private FeedDetailLoc feedDetailLoc;

    @BeforeEach
    void setup() {
        Feed feed = Feed.builder()
                .title("제주도 여행")
                .travelStart(LocalDateTime.of(2020, 1, 23, 1, 33))
                .travelEnd(LocalDateTime.of(2021, 1, 23, 1, 33))
                .feedDetail(null)
                .build();

        FeedDetail feedDetail = FeedDetail.builder()
                .feed(feed)
                .feedDetailLoc(null)
                .day("1일차")
                .build();

        FeedLocation feedLocation = FeedLocation.builder()
                .name("감귤농장")
                .latitude(1006L)
                .longitude(1054L)
                .placeAddress("서귀포시")
                .build();

        feedDetailLoc = FeedDetailLoc.builder()
                .memo("날씨가 좋아서 더 좋았던 제주도")
                .feedDetail(feedDetail)
                .feedLocation(feedLocation)
                .build();
    }

    @Test
    @DisplayName("피드상세위치이미지생성 - 정상케이스")
    void createFeedDetailLocImg_normal() {
        //given
        FeedDetailLoc location = feedDetailLoc;
        String filename = "cloudimage.jpg";
        String imgUrl = "image/cloudimage.jpg";

        //when
        FeedDetailLocImg feedDetailLocImg = FeedDetailLocImg.builder()
                .feedDetailLoc(location)
                .fileName(filename)
                .imgUrl(imgUrl)
                .build();

        //then
        assertNull(feedDetailLocImg.getId());
        assertEquals(location, feedDetailLocImg.getFeedDetailLoc());
        assertEquals(filename, feedDetailLocImg.getFileName());
        assertEquals(imgUrl, feedDetailLocImg.getImgUrl());
    }
}