package com.example.trip.service;

import com.example.trip.dto.request.PlanRequest;

public interface PlanService {

    void addPlan(PlanRequest.RegistDto planRegistRequestDto);
}
