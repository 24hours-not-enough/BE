package com.example.trip.advice;

import com.example.trip.dto.response.CalendarResponseDto;
import com.example.trip.dto.response.PlanResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetAllCalendarDetails {
    private boolean success;

    private String msg;

    private List<CalendarResponseDto> data;
}
