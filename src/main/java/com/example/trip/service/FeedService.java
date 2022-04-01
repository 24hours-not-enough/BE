package com.example.trip.service;

import com.example.trip.domain.*;
import com.example.trip.dto.response.FeedDetailLocResponseDto.GetFeedDetailLoc;
import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.dto.response.AllLocationsDto;

import java.util.*;

public interface FeedService {
    List<AllLocationsDto> findEachLocations(FeedRequestDto.FeedRequestMainGetDto feedRequestMainGetDto);
    List<Long> registerFeed(User user, FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto);
    void modifyFeed(User user, Long feedId, FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto);
    void deleteFeed(User user, Long feedId);
    List<FeedResponseDto.GetFeed> getFeeds(Long userId);
    List<Map<String, List<GetFeedDetailLoc>>> getLikeFeeds(Long userId);
}

