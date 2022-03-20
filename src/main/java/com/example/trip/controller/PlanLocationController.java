package com.example.trip.controller;

import com.example.trip.advice.Success;
import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.request.PlanLocationRequestDto;
import com.example.trip.service.PlanLocationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlanLocationController {

    private final PlanLocationService planLocationService;

    @ApiOperation(value = "가고싶은 장소 저장", notes = "로그인 사용자, 계획이 존재해야만 가능")
    @PostMapping("/plan/{planId}/location")
    public ResponseEntity<Success> PlanLocationAdd(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId, @RequestBody PlanLocationRequestDto dto) {
        planLocationService.addPlanLocation(planId, dto, userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true, "가고싶은 장소 저장성공!"), HttpStatus.OK);
    }

    @ApiOperation(value = "가고싶은 장소 삭제", notes = "로그인 사용자, 계획이 존재해야만 가능")
    @DeleteMapping("/plan/{planId}/location/{planLocationId}")
    public ResponseEntity<Success> PlanLocationRemove(@PathVariable Long planLocationId, @PathVariable Long planId, @RequestBody PlanLocationRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        planLocationService.removePlanLocation(planLocationId, planId, dto,userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true, "가고싶은 장소 삭제성공!"), HttpStatus.OK);
    }
}
