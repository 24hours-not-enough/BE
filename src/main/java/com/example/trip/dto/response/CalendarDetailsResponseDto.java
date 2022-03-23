package com.example.trip.dto.response;

import com.example.trip.domain.CalendarDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CalendarDetailsResponseDto {

    private Long calendarDetailsId;

    private String locationName;

    private String locationMemo;

    private String latitude;

    private String longitude;

    private int sort;

    public CalendarDetailsResponseDto(CalendarDetails calendarDetails) {
        this.calendarDetailsId = calendarDetails.getId();
        this.locationName = calendarDetails.getName();
        this.locationMemo = calendarDetails.getMemo();
        this.latitude = calendarDetails.getLatitude();
        this.longitude = calendarDetails.getLongitude();
        this.sort = calendarDetails.getSort();
    }

    @AllArgsConstructor
    @Getter
    public static class Plan {
        private Long calendarDetailsId;

        private String locationName;

        private String locationMemo;

        private int sort;

        public Plan(CalendarDetails calendarDetails) {
            this.calendarDetailsId = calendarDetails.getId();
            this.locationName = calendarDetails.getName();
            this.locationMemo = calendarDetails.getMemo();
            this.sort = calendarDetails.getSort();
        }
    }


}
