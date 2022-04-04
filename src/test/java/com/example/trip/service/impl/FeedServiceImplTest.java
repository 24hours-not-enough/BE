package com.example.trip.service.impl;

import com.example.trip.domain.*;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.repository.*;
import com.example.trip.repository.feed.FeedRepository;
import com.example.trip.repository.feedlocation.FeedLocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.trip.domain.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    FeedRepository feedRepository;
    @Mock
    FeedDetailRepository feedDetailRepository;
    @Mock
    FeedDetailLocRepository feedDetailLocRepository;
    @Mock
    FeedDetailLocImgRepository feedDetailLocImgRepository;
    @Mock
    FeedLocationRepository feedLocationRepository;

    private User user;
    private User save;

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


        when(userRepository.save(any(User.class))).thenReturn(user);
        save = userRepository.save(user);
    }

    @Test
    @DisplayName("피드 저장")
    void createFeed_normal() {
        //given
        FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto = new FeedRequestDto.FeedRequestRegisterDto(
                "제주도 여행기",
                LocalDateTime.of(2020, 1, 23, 1, 00),
                LocalDateTime.of(2020, 1, 30, 1, 00),
                null);

        Feed feed = Feed.builder()
                .user(user)
                .title(feedRequestRegisterDto.getTitle())
                .travelStart(feedRequestRegisterDto.getTravelStart())
                .travelEnd(feedRequestRegisterDto.getTravelEnd())
                .build();

        //when
        when(feedRepository.save(any(Feed.class))).thenReturn(feed);

        Feed result = feedRepository.save(feed);

        //then
        assertEquals(save.getId(), result.getUser().getId());
        assertEquals(feedRequestRegisterDto.getTitle(), feed.getTitle());
        assertEquals(feedRequestRegisterDto.getTravelStart(), feed.getTravelStart());
        assertEquals(feedRequestRegisterDto.getTravelEnd(), feed.getTravelEnd());
    }

    @Test
    @DisplayName("피드 상세 저장")
    void createFeedDetail_normal() {
        //given
        List<FeedDetail> details = new ArrayList<>();

        FeedDetail feedDetail_1 = FeedDetail.builder()
                .day("1일차")
                .build();

        FeedDetail feedDetail_2 = FeedDetail.builder()
                .day("2일차")
                .build();

        FeedDetail feedDetail_3 = FeedDetail.builder()
                .day("3일차")
                .build();

        details.add(feedDetail_1);
        details.add(feedDetail_2);
        details.add(feedDetail_3);

        FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto = new FeedRequestDto.FeedRequestRegisterDto(
                "제주도 여행기",
                LocalDateTime.of(2020, 1, 23, 1, 00),
                LocalDateTime.of(2020, 1, 30, 1, 00),
                details);

        Feed feed = Feed.builder()
                .user(user)
                .title(feedRequestRegisterDto.getTitle())
                .travelStart(feedRequestRegisterDto.getTravelStart())
                .travelEnd(feedRequestRegisterDto.getTravelEnd())
                .build();

        when(feedRepository.save(any(Feed.class))).thenReturn(feed);
        Feed saveFeed = feedRepository.save(feed);

        when(feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail())).thenReturn(details);

        feedRequestRegisterDto.getFeedDetail().stream().forEach(x -> x.setFeed(saveFeed));

        //when
        feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail());

        //then
        assertEquals(3, feedRequestRegisterDto.getFeedDetail().size());
        assertEquals(details.indexOf(0), feedRequestRegisterDto.getFeedDetail().indexOf(0));
        assertEquals(details.indexOf(1), feedRequestRegisterDto.getFeedDetail().indexOf(1));
        assertEquals(details.indexOf(2), feedRequestRegisterDto.getFeedDetail().indexOf(2));

    }

    @Test
    @DisplayName("게시글 저장")
    void createFeedDetailLoc_normal() {
        //given
        List<FeedDetailLoc> detailLocs = new ArrayList<>();

        FeedDetailLoc feedDetailLoc_1 = FeedDetailLoc.builder()
                .memo("감귤농장에서 귤 따기")
                .build();

        FeedDetailLoc feedDetailLoc_2 = FeedDetailLoc.builder()
                .memo("맛있는 해산물 시장")
                .build();

        detailLocs.add(feedDetailLoc_1);
        detailLocs.add(feedDetailLoc_2);

        List<FeedDetail> details = new ArrayList<>();

        FeedDetail feedDetail_1 = FeedDetail.builder()
                .feedDetailLoc(detailLocs)
                .day("1일차")
                .build();

        details.add(feedDetail_1);

        FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto = new FeedRequestDto.FeedRequestRegisterDto(
                "제주도 여행기",
                LocalDateTime.of(2020, 1, 23, 1, 00),
                LocalDateTime.of(2020, 1, 30, 1, 00),
                details);

        Feed feed = Feed.builder()
                .user(user)
                .title(feedRequestRegisterDto.getTitle())
                .travelStart(feedRequestRegisterDto.getTravelStart())
                .travelEnd(feedRequestRegisterDto.getTravelEnd())
                .build();

        when(feedRepository.save(any(Feed.class))).thenReturn(feed);
        Feed saveFeed = feedRepository.save(feed);

        feedRequestRegisterDto.getFeedDetail().stream().forEach(x -> x.setFeed(saveFeed));
        when(feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail())).thenReturn(details);
        feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail());

        feedRequestRegisterDto.getFeedDetail()
                .stream()
                .forEach(x -> x.getFeedDetailLoc()
                        .forEach(y -> y.setFeedDetail(x)));
        List<FeedDetailLoc> feedDetailLocs = details.stream().map(FeedDetail::getFeedDetailLoc).flatMap(Collection::stream).collect(Collectors.toList());

        when(feedDetailLocRepository.saveAll(feedDetailLocs)).thenReturn(detailLocs);

        //when
        feedDetailLocRepository.saveAll(feedDetailLocs);

        //then
        assertEquals(detailLocs.size(), feedDetailLocs.size());
        assertEquals(detailLocs.indexOf(0), feedDetailLocs.indexOf(0));
        assertEquals(detailLocs.indexOf(1), feedDetailLocs.indexOf(1));
    }

    @Test
    @DisplayName("위치 저장")
    void createFeedLocation_normal() {
        //given
        List<FeedLocation> locations = new ArrayList<>();

        FeedLocation feedLocation_1 = FeedLocation.builder()
                .name("감귤농장")
                .placeAddress("제주도 서귀포시")
                .latitude(1006L)
                .longitude(1054L)
                .build();

        FeedLocation feedLocation_2 = FeedLocation.builder()
                .name("해산물 시장")
                .placeAddress("제주도 제주시")
                .latitude(100L)
                .longitude(1000L)
                .build();

        locations.add(feedLocation_1);
        locations.add(feedLocation_2);



        List<FeedDetailLoc> detailLocs = new ArrayList<>();

        FeedDetailLoc feedDetailLoc_1 = FeedDetailLoc.builder()
                .memo("감귤농장에서 귤 따기")
                .feedLocation(feedLocation_1)
                .build();

        FeedDetailLoc feedDetailLoc_2 = FeedDetailLoc.builder()
                .memo("맛있는 해산물 시장")
                .feedLocation(feedLocation_2)
                .build();

        detailLocs.add(feedDetailLoc_1);
        detailLocs.add(feedDetailLoc_2);

        List<FeedDetail> details = new ArrayList<>();

        FeedDetail feedDetail_1 = FeedDetail.builder()
                .feedDetailLoc(detailLocs)
                .day("1일차")
                .build();

        details.add(feedDetail_1);

        FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto = new FeedRequestDto.FeedRequestRegisterDto(
                "제주도 여행기",
                LocalDateTime.of(2020, 1, 23, 1, 00),
                LocalDateTime.of(2020, 1, 30, 1, 00),
                details);

        Feed feed = Feed.builder()
                .user(user)
                .title(feedRequestRegisterDto.getTitle())
                .travelStart(feedRequestRegisterDto.getTravelStart())
                .travelEnd(feedRequestRegisterDto.getTravelEnd())
                .build();

        when(feedRepository.save(any(Feed.class))).thenReturn(feed);
        Feed saveFeed = feedRepository.save(feed);

        feedRequestRegisterDto.getFeedDetail().stream().forEach(x -> x.setFeed(saveFeed));
        when(feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail())).thenReturn(details);
        List<FeedDetail> feedDetails = feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail());

        feedRequestRegisterDto.getFeedDetail()
                .stream()
                .forEach(x -> x.getFeedDetailLoc()
                        .forEach(y -> y.setFeedDetail(x)));
        List<FeedDetailLoc> feedDetailLocs = feedDetails.stream().map(FeedDetail::getFeedDetailLoc).flatMap(Collection::stream).collect(Collectors.toList());

        when(feedDetailLocRepository.saveAll(feedDetailLocs)).thenReturn(detailLocs);
        feedDetailLocRepository.saveAll(feedDetailLocs);

        List<FeedLocation> feedLocations = feedDetailLocs.stream()
                .map(FeedDetailLoc::getFeedLocation)
                .collect(Collectors.toList());

        when(feedLocationRepository.saveAll(feedLocations)).thenReturn(locations);

        //when
        feedLocationRepository.saveAll(feedLocations);

        //then
        assertEquals(locations.size(), feedLocations.size());
        assertEquals(locations.indexOf(0), feedLocations.indexOf(0));
        assertEquals(locations.indexOf(1), feedLocations.indexOf(1));

    }

    @Test
    @DisplayName("게시글 이미지 저장")
    void createFeedDetailLocImg_normal() {
        //given

        List<FeedDetailLocImg> images = new ArrayList<>();

        FeedDetailLocImg image_1 = FeedDetailLocImg.builder()
                .fileName("tangerine.jpg")
                .imgUrl("/images/jeju/tangerine.jpg")
                .build();

        FeedDetailLocImg image_2 = FeedDetailLocImg.builder()
                .fileName("wind.jpg")
                .imgUrl("/images/jeju/wind.jpg")
                .build();

        images.add(image_1);
        images.add(image_2);

        List<FeedDetailLoc> detailLocs = new ArrayList<>();

        FeedDetailLoc feedDetailLoc_1 = FeedDetailLoc.builder()
                .memo("감귤농장에서 귤 따기")
                .feedDetailLocImg(images)
                .build();

        detailLocs.add(feedDetailLoc_1);

        List<FeedDetail> details = new ArrayList<>();

        FeedDetail feedDetail_1 = FeedDetail.builder()
                .feedDetailLoc(detailLocs)
                .day("1일차")
                .build();

        details.add(feedDetail_1);

        FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto = new FeedRequestDto.FeedRequestRegisterDto(
                "제주도 여행기",
                LocalDateTime.of(2020, 1, 23, 1, 00),
                LocalDateTime.of(2020, 1, 30, 1, 00),
                details);

        Feed feed = Feed.builder()
                .user(user)
                .title(feedRequestRegisterDto.getTitle())
                .travelStart(feedRequestRegisterDto.getTravelStart())
                .travelEnd(feedRequestRegisterDto.getTravelEnd())
                .build();

        when(feedRepository.save(any(Feed.class))).thenReturn(feed);
        Feed saveFeed = feedRepository.save(feed);

        feedRequestRegisterDto.getFeedDetail().stream().forEach(x -> x.setFeed(saveFeed));
        when(feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail())).thenReturn(details);
        feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail());

        feedRequestRegisterDto.getFeedDetail().stream()
                .forEach(x -> x.getFeedDetailLoc().forEach(y -> y.setFeedDetail(x)));
        List<FeedDetailLoc> feedDetailLocs = details.stream().map(FeedDetail::getFeedDetailLoc).flatMap(Collection::stream).collect(Collectors.toList());

        when(feedDetailLocRepository.saveAll(feedDetailLocs)).thenReturn(detailLocs);
        feedDetailLocRepository.saveAll(feedDetailLocs);

        feedRequestRegisterDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.getFeedDetailLocImg().forEach(z -> z.setFeedDetailLoc(y))));

        List<FeedDetailLocImg> feedDetailLocImgs = feedDetailLocs.stream()
                .map(FeedDetailLoc::getFeedDetailLocImg)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        when(feedDetailLocImgRepository.saveAll(feedDetailLocImgs)).thenReturn(images);

        //when
        feedDetailLocImgRepository.saveAll(feedDetailLocImgs);

        //then
        assertEquals(images.size(), feedDetailLocImgs.size());
        assertEquals(images.indexOf(0), feedDetailLocImgs.indexOf(0));
        assertEquals(images.indexOf(1), feedDetailLocImgs.indexOf(1));
    }

    @Test
    @DisplayName("수정 - 정상케이스")
    void modify_normal() {
        // given

        // when

        // then
    }
}