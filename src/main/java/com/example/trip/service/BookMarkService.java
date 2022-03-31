package com.example.trip.service;

import com.example.trip.domain.User;
import com.example.trip.dto.response.FeedLocationResponseDto;
import java.util.List;

public interface BookMarkService {
    void bookmarkFeed(Long feedLocationId, User user);
    void unbookmarkFeed(Long feedLocId, User user);
    List<FeedLocationResponseDto.BookMark> findBookMarkPlaces(Long userId);
}
