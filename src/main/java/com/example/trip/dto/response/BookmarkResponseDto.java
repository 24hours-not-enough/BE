package com.example.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookmarkResponseDto {
    private Long feeddetaillocId;
    private String location;
    private String city;
}
