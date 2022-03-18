package com.example.trip.service;

import com.example.trip.dto.request.CalendarDetailsRequestDto;
import com.example.trip.dto.response.CalendarResponseDto;

import java.util.List;

public interface CalendarDetailsService {
    void addCalendarDetails(Long calendar_id, CalendarDetailsRequestDto.Add dto, Long planId, Long userId);

    void modifyCalendarDetails(Long planId, Long calendarId, CalendarDetailsRequestDto.Modify dto, Long userId);

    void removeCalendarDetails(Long planId, Long calendarId, Long userId);

    List<CalendarResponseDto> findCalendarDetails(Long planId, Long calendarId, Long userId);

    void addCalendarDetailsAll(Long planId, List<CalendarDetailsRequestDto.AddAll> dto, Long id);
}
