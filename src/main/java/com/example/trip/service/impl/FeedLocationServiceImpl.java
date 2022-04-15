package com.example.trip.service.impl;

import com.example.trip.domain.FeedLocation;
import com.example.trip.dto.request.NewFeedLocationDto;
import com.example.trip.repository.FeedLocationRepository;
import com.example.trip.service.FeedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeedLocationServiceImpl implements FeedLocationService {
    private final FeedLocationRepository feedLocationRepository;
    @Override
    public Long registerNewFeedLocation(NewFeedLocationDto newFeedLocationDto){
FeedLocation feedLocation = FeedLocation.builder()
        .latitude(newFeedLocationDto.getLatitude())
        .longitude(newFeedLocationDto.getLongitude())
        .name(newFeedLocationDto.getName())
        .placeAddress(newFeedLocationDto.getPlaceAddress())
        .build();
        return feedLocationRepository.save(feedLocation).getId();
    }


}
