package com.example.trip.response;

import com.example.trip.feed.FeedResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReadOneTrip {
    private String result;
    private String msg;
    private FeedResponseDto.ReadOneTrip feed;
}
