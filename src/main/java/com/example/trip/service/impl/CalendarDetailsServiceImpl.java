package com.example.trip.service.impl;

import com.example.trip.domain.Calendar;
import com.example.trip.domain.CalendarDetails;
import com.example.trip.dto.request.CalendarDetailsDto;
import com.example.trip.repository.CalendarDetailsRepository;
import com.example.trip.repository.CalendarRepository;
import com.example.trip.service.CalendarDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CalendarDetailsServiceImpl implements CalendarDetailsService {

    private final CalendarDetailsRepository calendarDetailsRepository;

    private final CalendarRepository calendarRepository;
    @Override
    @Transactional
    public void addCalendarDetails(Long calendar_id, CalendarDetailsDto dto) {
        Optional<Calendar> findByCalendar = calendarRepository.findById(calendar_id);
        CalendarDetails calendarDetails = CalendarDetails.builder()
                .calendar(findByCalendar.get())
                .name(dto.getLocation())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .build();
        calendarDetailsRepository.save(calendarDetails);
    }
}
