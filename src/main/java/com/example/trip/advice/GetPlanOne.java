package com.example.trip.advice;

import com.example.trip.dto.response.PlanResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetPlanOne {
    private boolean success;

    private String msg;

    private PlanResponseDto data;
}