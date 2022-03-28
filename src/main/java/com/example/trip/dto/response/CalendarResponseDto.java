package com.example.trip.dto.response;

import com.example.trip.domain.Calendar;
import com.example.trip.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CalendarResponseDto {

    private Long calendarId;

    private String days;

    private List<CalendarDetailsResponseDto> calendarDetails;

    public CalendarResponseDto(Calendar calendar) {
        this.calendarId = calendar.getId();
        this.days = calendar.getDays();
        this.calendarDetails = calendar.getCalendarDetails().stream()
                .map(CalendarDetailsResponseDto::new)
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Getter
    public static class Plan {
        private Long calendarId;

        private String days;

        private List<CalendarDetailsResponseDto.Plan> calendarDetails;

        public Plan(Calendar calendar) {
            this.calendarId = calendar.getId();
            this.days = calendar.getDays();
            this.calendarDetails = calendar.getCalendarDetails().stream()
                    .map(CalendarDetailsResponseDto.Plan::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Builder
    public static class CalendarAdd{
        private Long calendarId;
    }
}
