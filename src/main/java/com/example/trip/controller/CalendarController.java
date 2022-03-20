package com.example.trip.controller;

import com.example.trip.advice.Success;
import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.service.CalendarService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalendarController {

    private final CalendarService calendarService;

    @ApiOperation(value = "일정 추가", notes = "계획이 존재해야만 추가 가능")// 해당 노트가 있는 프로젝트의 참여자만 조회 가능
    @PostMapping("/plan/{planId}/days")
    public ResponseEntity<Success> daysAdd(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId){
        calendarService.addDays(planId, userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true,"일차 추가 완료!"), HttpStatus.OK);
    }


    @ApiOperation(value = "일정 및 상세계획 잠금", notes = "계획이 존재해야만 잠금 가능 하며 잠금 여부 표시")
    @PutMapping("/plan/{planId}/days")
    public ResponseEntity<Success> CalendarLocked(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId){
        calendarService.addCalendarLock(planId, userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true,"상세계획 내용 잠금 완료!"), HttpStatus.OK);
    }
}
