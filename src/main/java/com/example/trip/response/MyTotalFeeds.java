package com.example.trip.response;

import com.example.trip.feed.FeedResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MyTotalFeeds {
    private String result;
    private String msg;
    private List<FeedResponseDto.AllMyTrips> feeds;
}
