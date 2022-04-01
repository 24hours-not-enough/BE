package com.example.trip.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.example.trip.domain.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

class FeedCommentTest {

    private FeedDetailLoc feedDetailLoc;
    private  User user;

    @BeforeEach
    void setup() {

       user = User.builder()
                .email("kim@naver.com")
                .username("babo")
                .password("1234")
                .memberstatus(true)
                .socialaccountId("KAKAO_1234")
                .role(USER)
                .image(Image.builder().build())
                .build();

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
                .latitude("100.356")
                .longitude("46.87")
                .placeAddress("서귀포시")
                .build();

        feedDetailLoc = FeedDetailLoc.builder()
                .memo("날씨가 좋아서 더 좋았던 제주도")
                .feedDetail(feedDetail)
                .feedLocation(feedLocation)
                .build();
    }

    @Test
    @DisplayName("피드댓글생성 - 정상케이스")
    void createFeedComment_normal() {
        //given
        FeedDetailLoc feedDetailLoc = this.feedDetailLoc;
        String content = "나랑 같이 가자 제주도";
        User user1 = user;

        //when
        FeedComment feedComment = FeedComment.builder()
                .feedDetailLoc(feedDetailLoc)
                .content(content)
                .user(user1)
                .build();

        //then
        assertNull(feedComment.getId());
        assertEquals(feedDetailLoc, feedComment.getFeedDetailLoc());
        assertEquals(content, feedComment.getContent());
        assertEquals(user1, feedComment.getUser());
    }
}