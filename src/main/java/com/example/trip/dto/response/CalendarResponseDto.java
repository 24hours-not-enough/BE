package com.example.trip.dto.response;

import com.example.trip.domain.Calendar;
import com.example.trip.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CalendarResponseDto {

    private Long calendar_id;

    private String days;

    private List<CalendarDetailsResponseDto> calendarDetails;

    public CalendarResponseDto(Calendar calendar) {
        this.calendar_id = calendar.getId();
        this.days = calendar.getDays();
        this.calendarDetails = calendar.getCalendarDetails().stream()
                .map(CalendarDetailsResponseDto::new)
                .collect(Collectors.toList());
    }
}