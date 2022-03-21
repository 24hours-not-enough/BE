package com.example.trip.response;

import com.example.trip.dto.BookmarkResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BookmarkLocation {
    private String result;
    private String msg;
    private List<BookmarkResponseDto> bookmarkdata;
}
