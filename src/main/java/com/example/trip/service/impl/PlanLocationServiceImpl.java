package com.example.trip.service.impl;

import com.example.trip.advice.exception.AuthPlanNotFoundException;
import com.example.trip.advice.exception.LocationNotFoundException;
import com.example.trip.advice.exception.PlanNotFoundException;
import com.example.trip.domain.Plan;
import com.example.trip.domain.PlanLocation;
import com.example.trip.dto.request.PlanLocationRequestDto;
import com.example.trip.repository.MemberRepository;
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

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void addPlanLocation(Long planId, PlanLocationRequestDto dto, Long userId) {
        Optional<Plan> findPlan = planRepository.findById(planId);
        planValidation(planId);
        authPlanValidation(planId,userId);
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
    public void removePlanLocation(Long planLocationId, Long planId, PlanLocationRequestDto dto, Long userId) {
        planValidation(planId);
        locationValidation(planLocationId);
        authPlanValidation(planId,userId);
        planLocationRepository.deleteById(planLocationId);
    }

    private void planValidation(Long planId) {
        planRepository.findById(planId).orElseThrow(PlanNotFoundException::new);
    }

    private void locationValidation(Long planLocationId){
        planLocationRepository.findById(planLocationId).orElseThrow(LocationNotFoundException::new);
    }

    private void authPlanValidation(Long planId, Long userId) {
        memberRepository.findByUserAndPland(userId,planId).orElseThrow(AuthPlanNotFoundException::new);
    }
}
