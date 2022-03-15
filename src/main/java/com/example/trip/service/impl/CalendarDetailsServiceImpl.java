package com.example.trip.service.impl;

import com.example.trip.domain.Calendar;
import com.example.trip.domain.CalendarDetails;
import com.example.trip.dto.request.CalendarDetailsRequestDto;
import com.example.trip.dto.response.CalendarResponseDto;
import com.example.trip.repository.CalendarDetailsRepository;
import com.example.trip.repository.CalendarRepository;
import com.example.trip.service.CalendarDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CalendarDetailsServiceImpl implements CalendarDetailsService {

    private final CalendarDetailsRepository calendarDetailsRepository;

    private final CalendarRepository calendarRepository;
    @Override
    @Transactional
    public void addCalendarDetails(Long calendar_id, CalendarDetailsRequestDto.Add dto) {
        Optional<Calendar> findByCalendar = calendarRepository.findById(calendar_id);
        CalendarDetails calendarDetails = CalendarDetails.builder()
                .calendar(findByCalendar.get())
                .name(dto.getLocation())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .build();
        calendarDetailsRepository.save(calendarDetails);
    }

    @Override
    @Transactional
    public void modifyCalendarDetails(Long planId, Long calendarId, CalendarDetailsRequestDto.Modify dto) {
        Optional<CalendarDetails> findByDetails = calendarDetailsRepository.findById(calendarId);
        Optional<Calendar> findcalendarId = calendarRepository.findById(calendarId);
        calendarDetailsRepository.deleteByCalendarId(calendarId);
        setCalendarDetails(findcalendarId.get(),dto);
    }

    @Override
    @Transactional
    public void removeCalendarDetails(Long planId, Long calendarId) {
        calendarDetailsRepository.deleteByCalendarId(calendarId);
    }

    @Override
    public List<CalendarResponseDto> findCalendarDetails(Long planId, Long calendarId) {
        return calendarRepository.findByPlanIdAndCalendarId(planId).stream()
                .map(CalendarResponseDto::new)
                .collect(Collectors.toList());
    }

    private void setCalendarDetails(Calendar calendar, CalendarDetailsRequestDto.Modify dto) {
        dto.getDetailsList().forEach((detailsList)->{
            CalendarDetails calendarDetails = CalendarDetails.builder()
                    .calendar(calendar)
                    .name(detailsList.getLocation())
                    .latitude(detailsList.getLatitude())
                    .longitude(detailsList.getLongitude())
                    .memo(detailsList.getLocationMemo())
                    .order(detailsList.getSort())
                    .build();
            calendarDetailsRepository.save(calendarDetails);
        });
    }
}
