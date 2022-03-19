package com.example.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FeedCommentResponseDto {
    private String username;
    private String content;
}
