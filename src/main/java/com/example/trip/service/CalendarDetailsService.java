package com.example.trip.service;

import com.example.trip.dto.request.CalendarDetailsRequestDto;
import com.example.trip.dto.response.CalendarResponseDto;

import java.util.List;

public interface CalendarDetailsService {
    void addCalendarDetails(Long calendar_id, CalendarDetailsRequestDto.Add dto);

    void modifyCalendarDetails(Long planId, Long calendarId, CalendarDetailsRequestDto.Modify dto);

    void removeCalendarDetails(Long planId, Long calendarId);

    List<CalendarResponseDto> findCalendarDetails(Long planId, Long calendarId);
}
