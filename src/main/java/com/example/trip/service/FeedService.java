package com.example.trip.service;

import com.example.trip.advice.exception.*;
import com.example.trip.domain.*;
import com.example.trip.dto.response.FeedDetailLocResponseDto.GetFeedDetailLoc;
import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.dto.response.AllLocationsDto;
import com.example.trip.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

public interface FeedService {
    List<AllLocationsDto> findAll();
    List<Long> registerFeed(User user, FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto);
    void modifyFeed(User user, Long feedId, FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto);
    void deleteFeed(User user, Long feedId);
    List<FeedResponseDto.GetFeed> getFeeds(Long userId);
    List<Map<String, List<GetFeedDetailLoc>>> getLikeFeeds(Long userId);
}

