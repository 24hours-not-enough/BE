package com.example.trip.controller;

import com.example.trip.advice.Success;
import com.example.trip.dto.request.PlanLocationRequestDto;
import com.example.trip.service.PlanLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlanLocationController {

    private final PlanLocationService planLocationService;

    @PostMapping("/{planId}/location")
    public ResponseEntity<Success> PlanLocationAdd(@PathVariable Long planId, @RequestBody PlanLocationRequestDto dto) {
        planLocationService.addPlanLocation(planId, dto);
        return new ResponseEntity<>(new Success(true, "가고싶은 장소 저장성공!"), HttpStatus.OK);
    }

    @DeleteMapping("/plan/location/{planLocationId}")
    public ResponseEntity<Success> PlanLocationRemove(@PathVariable Long planLocationId, @RequestBody PlanLocationRequestDto dto) {
        planLocationService.removePlanLocation(planLocationId, dto);
        return new ResponseEntity<>(new Success(true, "가고싶은 장소 삭제성공!"), HttpStatus.OK);
    }
}
