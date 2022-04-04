package com.example.trip.repository.feed;

import com.example.trip.dto.response.FeedResponseDto;

import java.util.List;

public interface FeedCustomRepository {
    List<FeedResponseDto.GetFeed> findByUserId(Long userId);
}
