package com.example.trip.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CalendarDetailsRequestDto {

    private String location;

    private String locationMemo;

    private String latitude;

    private String longitude;

    private int sort;

    @Getter
    @NoArgsConstructor
    public static class Add{
        private String location;

        private String latitude;

        private String longitude;
    }

    @Getter
    @NoArgsConstructor
    public static class Modify{
        List<CalendarDetailsRequestDto> detailsList;
    }
}
