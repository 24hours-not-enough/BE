package com.example.trip.advice;

import com.example.trip.dto.response.PlanResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetAllPlan {
    private boolean success;

    private String msg;

    private List<PlanResponseDto> data;
}