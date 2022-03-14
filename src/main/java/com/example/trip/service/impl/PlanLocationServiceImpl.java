package com.example.trip.service.impl;

import com.example.trip.domain.Plan;
import com.example.trip.domain.PlanLocation;
import com.example.trip.dto.request.PlanLocationRequestDto;
import com.example.trip.repository.PlanLocationRepository;
import com.example.trip.repository.plan.PlanRepository;
import com.example.trip.service.PlanLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlanLocationServiceImpl implements PlanLocationService {

    private final PlanLocationRepository planLocationRepository;

    private final PlanRepository planRepository;

    @Override
    @Transactional
    public void addPlanLocation(Long planId, PlanLocationRequestDto dto) {
        Optional<Plan> findPlan = planRepository.findById(planId);
        PlanLocation plan = PlanLocation.builder()
                .plan(findPlan.get())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .name(dto.getLocation())
                .build();
        planLocationRepository.save(plan);
    }

    @Override
    @Transactional
    public void removePlanLocation(Long planLocationId, PlanLocationRequestDto dto) {
        planLocationRepository.deleteById(planLocationId);
    }
}
