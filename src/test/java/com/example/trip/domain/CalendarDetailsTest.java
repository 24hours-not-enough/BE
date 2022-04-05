package com.example.trip.domain;

import com.example.trip.dto.request.CalendarDetailsRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalendarDetailsTest {
    Long id;
    String name;
    String memo;
    String latitude;
    String longitude;
    String location;
    int sort;


    @BeforeEach
    void init(){
        id = 1L;
        name = "체크아이템";
        memo = "메모";
        latitude = "위도";
        longitude = "경도";
        location = "위치";
        sort = 1;

    }

    @Test
    @DisplayName("캘린더 디테일 등록 정상 케이스")
    void createCalendarDetail_Normal() {

        CalendarDetailsRequestDto.Add requestDto = CalendarDetailsRequestDto.Add.builder()
                .location(location)
                .latitude(latitude)
                .longitude(longitude)
                .build();

// when
        CalendarDetails calendarDetails = CalendarDetails.builder()
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .build();

// then
        assertNull(calendarDetails.getMemo());
        assertNull(calendarDetails.getName());
        assertEquals(latitude, requestDto.getLatitude());
        assertEquals(longitude, requestDto.getLongitude());
    }

}
