package com.example.trip.service.impl;

import com.example.trip.domain.Calendar;
import com.example.trip.domain.Plan;
import com.example.trip.dto.request.CalendarRequestDto;
import com.example.trip.repository.CalendarRepository;
import com.example.trip.repository.plan.PlanRepository;
import com.example.trip.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;

    private final PlanRepository planRepository;
    @Override
    @Transactional
    public void addDays(Long planId) {
        Optional<Plan> findPlan = planRepository.findById(planId);
        Calendar calendar = Calendar.builder()
                .days("ex) 1일차")
                .plan(findPlan.get())
                .build();
        calendarRepository.save(calendar);
    }
}
