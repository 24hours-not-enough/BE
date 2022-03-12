package com.example.trip.service;

import com.example.trip.dto.request.PlanRequestDto;
import com.example.trip.dto.response.PlanResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlanService {

    Long addPlan(PlanRequestDto.Regist dto);

    List<PlanResponseDto> findPlan(Pageable pageable);

    void modifyPlan(Long planId, PlanRequestDto.Modify modify);
}
