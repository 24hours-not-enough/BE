package com.example.trip.service;

import com.example.trip.dto.request.CalendarDetailsDto;

public interface CalendarDetailsService {
    void addCalendarDetails(Long calendar_id, CalendarDetailsDto dto);
}
