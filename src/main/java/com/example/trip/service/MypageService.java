package com.example.trip.service;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MypageService {

    List<FeedResponseDto.AllMyTrips> showAllMyFeeds(Long userId);

    List<BookmarkResponseDto> getBookmarkPlaces(Long userId);

    FeedDetailLocResponseDto.ReadOneFeed readOneFeed(Long feeddetaillocId);

    List<LikesResponseDto.SortByCity> sortLikesFeed(String socialaccountId);

    FeedResponseDto.ReadOneTrip readOneTrip(Long userId, Long feedId);

    UserResponseDto.UserProfile changeProfile(UserDetailsImpl userDetails, String username, MultipartFile file) throws IOException;

    MypageResponseDto.GetPlan getPlan(Long planId, Long userId);
}
