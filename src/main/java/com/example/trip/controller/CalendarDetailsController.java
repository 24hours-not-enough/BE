package com.example.trip.controller;

import com.example.trip.advice.Success;
import com.example.trip.dto.request.CalendarDetailsDto;
import com.example.trip.service.CalendarDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalendarDetailsController {

    private final CalendarDetailsService calendarDetailsService;

    @PostMapping("/plan/{calendar_id}")
    public ResponseEntity<Success> CalendarDetailsAdd(@PathVariable Long calendar_id, @RequestBody CalendarDetailsDto dto){
        calendarDetailsService.addCalendarDetails(calendar_id,dto);
        return new ResponseEntity<>(new Success(true,"가고 싶은 곳 추가 완료!"), HttpStatus.OK);
    }

}
