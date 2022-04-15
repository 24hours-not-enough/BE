package com.example.trip.service;

import com.example.trip.domain.FeedLocation;
import com.example.trip.dto.request.NewFeedLocationDto;

public interface FeedLocationService {
    Long registerNewFeedLocation(NewFeedLocationDto newFeedLocationDto);
}
