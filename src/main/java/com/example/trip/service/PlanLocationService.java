package com.example.trip.service;

import com.example.trip.dto.request.PlanLocationRequestDto;

public interface PlanLocationService {
    void addPlanLocation(Long planId, PlanLocationRequestDto dto, Long userId);

    void removePlanLocation(Long planLocationId, Long planId, PlanLocationRequestDto dto, Long userId);
}
