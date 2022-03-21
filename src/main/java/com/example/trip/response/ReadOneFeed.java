package com.example.trip.response;

import com.example.trip.dto.FeedDetailLocCommentResponseDto;
import com.example.trip.dto.FeedDetailLocResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReadOneFeed {
    private String result;
    private String msg;
    private FeedDetailLocResponseDto.ReadOneFeed feeddata;
}
