package com.example.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FeedDetailLocCommentResponseDto {
    private String location;
    private String city;
    private String content;
    private List<String> images;
    private List<FeedCommentResponseDto> comments;
}
