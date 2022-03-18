package com.example.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class FeedDetailResponseDto {
    private Long feeddetailId;
    private String day;
    private String memo;
    private List<FeedDetailLocResponseDto> feeddetailloc;

}
