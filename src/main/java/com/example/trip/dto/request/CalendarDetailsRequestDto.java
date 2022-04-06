package com.example.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CalendarDetailsRequestDto {

    private String locationName;

    private String locationMemo;

    private String latitude;

    private String longitude;

    private int sort;

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Add{
        private String location;

        private String latitude;

        private String longitude;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Modify{
        List<CalendarDetailsRequestDto> detailsList;
    }

    @Getter
    @NoArgsConstructor
    public static class AddAll{
        private Long calendarId;

        private String days;

        private List<CalendarDetailsRequestDto> calendarDetails;
    }
}

