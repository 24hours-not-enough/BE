package com.example.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class LikesResponseDto {

    @AllArgsConstructor
    @Getter
    public static class SortByCity {
        private String city;
        private int totalfeed;
        private List<String> images;
    }
}
