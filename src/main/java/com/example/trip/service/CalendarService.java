package com.example.trip.service;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.response.CalendarResponseDto;

public interface CalendarService {
    CalendarResponseDto.CalendarAdd addDays(Long planId, Long userId);

    void addCalendarLock(Long planId, Long userId);

    void addCalendarUnLock(Long planId, Long userId);

    void removeDays(Long userId, Long planId, Long calendarId);
}
