package com.example.trip.service.impl;

import com.example.trip.advice.exception.AuthBookMarkNotFoundException;
import com.example.trip.advice.exception.FeedDetailLocNotFoundException;
import com.example.trip.domain.BookMark;
import com.example.trip.domain.FeedLocation;
import com.example.trip.domain.User;
import com.example.trip.dto.response.FeedLocationResponseDto;
import com.example.trip.repository.BookMarkRepository;
import com.example.trip.repository.feedlocation.FeedLocationRepository;
import com.example.trip.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BookMarkServiceImpl implements BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final FeedLocationRepository feedLocationRepository;

    @Override
    public void bookmarkFeed(Long feedLocationId, User user) {
        // 해당 피드 상세 위치 값이 있는지 체크
        FeedLocation feedLocation = feedLocationRepository.findById(feedLocationId)
                .orElseThrow(() -> new FeedDetailLocNotFoundException());
        BookMark bookmark = BookMark.builder()
                .feedLocation(feedLocation)
                .user(user)
                .build();
        //북마크 저장
        bookMarkRepository.save(bookmark);
    }

    @Override
    public void unbookmarkFeed(Long feedLocId, User user) {
        // 북마크를 한 사람만 권한이 있어야함
        List<BookMark> myBookmark = bookMarkRepository.findByFeedLocIdAndUserId(feedLocId, user.getId());

        if (myBookmark.isEmpty()) {
            throw new AuthBookMarkNotFoundException();
        }
        //북마크 삭제
        bookMarkRepository.deleteBookmarkFeed(feedLocId, user.getId());
    }

    @Override
    public List<FeedLocationResponseDto.BookMark> findBookMarkPlaces(Long userId) {
        ArrayList<FeedLocationResponseDto.BookMark> arr = new ArrayList<>();

        feedLocationRepository.findFeedLocationByBookMarkByUser(userId).stream()
                .map(x -> arr.add(new FeedLocationResponseDto.BookMark(x)))
                .collect(Collectors.toList());

        return arr;
    }
}
