package com.example.trip.response;

import com.example.trip.dto.LikesResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SortLikesFeed {
    private String result;
    private String msg;
    private List<LikesResponseDto.SortByCity> likesdata;
}
