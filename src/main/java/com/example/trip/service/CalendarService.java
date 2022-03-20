package com.example.trip.service;

public interface CalendarService {
    void addDays(Long planId, Long userId);

    void addCalendarLock(Long planId, Long userId);
}
