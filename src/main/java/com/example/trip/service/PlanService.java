package com.example.trip.service;

import com.example.trip.dto.request.PlanRequestDto;
import com.example.trip.dto.response.PlanResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlanService {

    Long addPlan(Long user_id, PlanRequestDto.Regist dto);

    List<PlanResponseDto> findPlan(Long userId, Pageable pageable);

    void modifyPlan(Long user_id, Long planId, PlanRequestDto.Modify modify);

    PlanResponseDto findPlanOne(Long userId, Long planId);

    void removePlanMember(Long id, Long planId);

    void removePlan(Long userId, Long planId);

    List<PlanResponseDto.DetailAll> findPlanAllAndMember(Long userId, Long planId);
}
