package com.example.trip.response;

import com.example.trip.dto.FeedDetailLocCommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReadOneFeed {
    private String result;
    private String msg;
    private FeedDetailLocCommentResponseDto feeddata;
}
