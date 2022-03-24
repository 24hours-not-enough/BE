package com.example.trip.dto.response;

import com.example.trip.domain.Plan;
import com.example.trip.dto.response.CalendarResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MypageResponseDto {

    @Getter
    @AllArgsConstructor
    public static class GetPlan{
        private Long planId;
        private String title;
        private LocalDateTime travel_start;
        private LocalDateTime travel_end;
        private List<CalendarResponseDto.Plan> calendars;

        public GetPlan(Plan plan) {
            this.planId = plan.getId();
            this.title = plan.getTitle();
            this.travel_start = plan.getTravel_start();
            this.travel_end = plan.getTravel_end();
            this.calendars = plan.getCalendars().stream()
                    .map(CalendarResponseDto.Plan::new).collect(Collectors.toList());
        }
    }

    @Getter
    @Builder
    public static class Response {
        private String result;
        private String msg;
        private Object data;
    }
}
