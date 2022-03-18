package com.example.trip.service;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface MypageService {

    List<FeedResponseDto.AllMyTrips> showAllMyFeeds(@AuthenticationPrincipal UserDetailsImpl userDetails);

//    List<BookmarkResponseDto> getBookmarkPlaces(UserDetailsImpl userDetails);

    FeedDetailLocCommentResponseDto readOneFeed(Long feeddetaillocId);

    List<LikesResponseDto.SortByCity> sortLikesFeed(UserDetailsImpl userDetails);

    FeedResponseDto.ReadOneTrip readOneTrip(UserDetailsImpl userDetails, Long feedId);
}
