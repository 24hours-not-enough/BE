package com.example.trip.dto.response;

import com.example.trip.domain.CalendarDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CalendarDetailsResponseDto {

    private Long calendar_details_id;

    private String location_name;

    private String location_memo;

    private String latitude;

    private String longitude;

    private int order;

    public CalendarDetailsResponseDto(CalendarDetails calendarDetails) {
        this.calendar_details_id = calendarDetails.getId();
        this.location_name = calendarDetails.getName();
        this.location_memo = calendarDetails.getMemo();
        this.latitude = calendarDetails.getLatitude();
        this.longitude = calendarDetails.getLongitude();
        this.order = calendarDetails.getOrder();
    }

    @AllArgsConstructor
    @Getter
    public static class Plan {
        private Long calendar_details_id;

        private String location_name;

        private String location_memo;

        private int order;

        public Plan(CalendarDetails calendarDetails) {
            this.calendar_details_id = calendarDetails.getId();
            this.location_name = calendarDetails.getName();
            this.location_memo = calendarDetails.getMemo();
            this.order = calendarDetails.getOrder();
        }
    }


}
