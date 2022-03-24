package com.example.trip.service;

import com.example.trip.advice.exception.FeedDetailLocNotFoundException;
import com.example.trip.domain.BookMark;
import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.FeedLocation;
import com.example.trip.domain.User;
import com.example.trip.dto.response.FeedLocationResponseDto;
import com.example.trip.repository.BookMarkRepository;
import com.example.trip.repository.FeedDetailLocRepository;
import com.example.trip.repository.FeedLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final FeedDetailLocRepository feedDetailLocRepository;
    private final FeedLocationRepository feedLocationRepository;

    public void bookmarkFeed(Long feedLocationId, User user) {
        // 해당 피드 상세 위치 값이 있는지 체크
        FeedLocation feedLocation = feedLocationRepository.findById(feedLocationId)
                .orElseThrow(() -> new FeedDetailLocNotFoundException());
        BookMark bookmark = BookMark.builder()
                .feedLocation(feedLocation)
                .user(user)
                .build();

        bookMarkRepository.save(bookmark);
    }

    public void unbookmarkFeed(Long feedLocId, User user) {
        // 북마크를 한 사람만 권한이 있어야함
        List<BookMark> myBookmark = bookMarkRepository.findByFeedLocIdAndUserId(feedLocId, user.getId());

        if (myBookmark.isEmpty()) {
            throw new AuthBookMarkNotFoundException();
        }
        bookMarkRepository.deleteBookmarkFeed(feedLocId, user.getId());
    }

    public List<FeedLocationResponseDto.BookMark> findBookMarkPlaces(Long userId) {

        List<BookMark> bookMarks = bookMarkRepository.findByUserId(userId);
        ArrayList<FeedLocationResponseDto.BookMark> arr = new ArrayList<>();
        for (BookMark bookMark : bookMarks) {
            Optional<FeedLocation> location = feedLocationRepository.findById(bookMark.getFeedLocation().getId());
            FeedLocationResponseDto.BookMark bookMarkLoc = new FeedLocationResponseDto.BookMark(location.get());
            arr.add(bookMarkLoc);
        }
        return arr;

    }
}
