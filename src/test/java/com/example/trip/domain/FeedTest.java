package com.example.trip.domain;

import com.example.trip.dto.request.FeedRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.trip.domain.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

class FeedTest {

    @Test
    @DisplayName("피드생성 - 정상케이스")
    void createFeed_Normal() {

        //given
        User user = User.builder()
                .email("kim@naver.com")
                .username("babo")
                .password("1234")
                .memberstatus(true)
                .socialaccountId("KAKAO_1234")
                .role(USER)
                .image(Image.builder().build())
                .build();

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

        //when
        Feed feed = Feed.builder()
                .user(user)
                .title(dto.getTitle())
                .travelStart(dto.getTravelStart())
                .travelEnd(dto.getTravelEnd())
                .feedDetail(null)
                .build();

        //then
        assertNull(feed.getId());
        assertNull(feed.getFeedDetail());
        assertEquals(user, feed.getUser());
        assertEquals(title, feed.getTitle());
        assertEquals(travelStart, feed.getTravelStart());
        assertEquals(travelEnd, feed.getTravelEnd());

    }

    @Test
    @DisplayName("피드수정 - 정상케이스")
    void updateFeed_normal() {
        //given
        Feed feed = Feed.builder()
                .title("제주도 여행")
                .travelStart(LocalDateTime.of(2020, 1, 23, 1, 33))
                .travelEnd(LocalDateTime.of(2021, 1, 23, 1, 33))
                .feedDetail(null)
                .build();

        FeedRequestDto.FeedRequestModifyDto dto = new FeedRequestDto.FeedRequestModifyDto(
                "울릉도 여행",
                LocalDateTime.of(2020, 2, 23, 1, 33),
                LocalDateTime.of(2021, 1, 23, 1, 33),
                null
        );

        //when
        feed.update(dto);

        //then
        assertEquals(feed.getTitle(), dto.getTitle());
        assertEquals(feed.getTravelStart(), dto.getTravelStart());
        assertEquals(feed.getTravelEnd(), dto.getTravelEnd());
    }
}