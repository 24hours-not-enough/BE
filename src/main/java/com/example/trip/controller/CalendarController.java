package com.example.trip.controller;

import com.example.trip.advice.Success;
import com.example.trip.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping("/plan/{planId}/days")
    public ResponseEntity<Success> daysAdd(@PathVariable Long planId){
        calendarService.addDays(planId);
        return new ResponseEntity<>(new Success(true,"일차 추가 완료!"), HttpStatus.OK);
    }
}
