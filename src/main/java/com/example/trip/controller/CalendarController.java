package com.example.trip.controller;

import com.example.trip.advice.Success;
import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.service.CalendarService;
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

    @PostMapping("/plan/{planId}/days")
    public ResponseEntity<Success> daysAdd(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId){
        calendarService.addDays(planId, userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true,"일차 추가 완료!"), HttpStatus.OK);
    }

    @PutMapping("/plan/{planId}/days")
    public ResponseEntity<Success> CalendarLocked(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId){
        calendarService.addCalendarLock(planId, userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true,"상세계획 내용 잠금 완료!"), HttpStatus.OK);
    }
}
