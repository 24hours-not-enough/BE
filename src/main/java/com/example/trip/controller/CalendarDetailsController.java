package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.request.CalendarDetailsRequestDto;
import com.example.trip.dto.response.CalendarResponseDto;
import com.example.trip.dto.response.PlanResponseDto;
import com.example.trip.service.CalendarDetailsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalendarDetailsController {

    private final CalendarDetailsService calendarDetailsService;

    @ApiOperation(value = "가고 싶은 곳 상세 계획에 추가", notes = "계획 및 일정이 존재해야만 추가 가능")
    @PostMapping("/plan/{planId}/{calendarId}")
    public ResponseEntity<PlanResponseDto.ResponseNodata> CalendarDetailsAdd(@PathVariable Long calendarId, @RequestBody CalendarDetailsRequestDto.Add dto, @PathVariable Long planId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        calendarDetailsService.addCalendarDetails(calendarId, dto, planId, userDetails.getUser().getId());
        return new ResponseEntity<>(PlanResponseDto.ResponseNodata.builder()
                .msg("상세계획 내용 등록 완료!")
                .result("success")
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "가고 싶은 곳 상세 계획 수정", notes = "계획 및 일정이 존재해야만 수정 가능")
    @PutMapping("/plan/{planId}/days/{calendarId}")
    public ResponseEntity<PlanResponseDto.ResponseNodata> CalendarDetailsModify(@PathVariable Long planId, @PathVariable Long calendarId, @RequestBody CalendarDetailsRequestDto.Modify dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        calendarDetailsService.modifyCalendarDetails(planId, calendarId, dto, userDetails.getUser().getId());
        return new ResponseEntity<>(PlanResponseDto.ResponseNodata.builder()
                .msg("상세계획 내용 수정 완료!")
                .result("success")
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "가고 싶은 곳 상세 계획 삭제", notes = "계획 및 일정이 존재해야만 삭제 가능")
    @DeleteMapping("/plan/{planId}/days/{calendarId}")
    public ResponseEntity<PlanResponseDto.ResponseNodata> CalendarDetailsRemove(@PathVariable Long planId, @PathVariable Long calendarId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        calendarDetailsService.removeCalendarDetails(planId, calendarId, userDetails.getUser().getId());
        return new ResponseEntity<>(PlanResponseDto.ResponseNodata.builder()
                .msg("상세계획 내용 삭제 완료!")
                .result("success")
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "가고 싶은 곳 상세 계획 조회", notes = "계획 및 일정이 존재해야만 조회 가능")
    @GetMapping("/plan/{planId}/days/{calendarId}")
    public ResponseEntity<PlanResponseDto.Response> CalendarDetailsList(@PathVariable Long planId, @PathVariable Long calendarId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<CalendarResponseDto> calendarDetails = calendarDetailsService.findCalendarDetails(planId, calendarId, userDetails.getUser().getId());
        return new ResponseEntity<>(PlanResponseDto.Response.builder()
                .msg("상세계획 내용 조회 완료!")
                .result("success")
                .data(calendarDetails)
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "가고 싶은 곳 상세 계획 전체 한번에 등록", notes = "계획 및 일정이 존재해야만 등록 가능")
    @PostMapping("/plan/{planId}/days/calendar")
    public ResponseEntity<PlanResponseDto.ResponseNodata> CalendarDetailsAddAll(@PathVariable Long planId, @RequestBody List<CalendarDetailsRequestDto.AddAll> dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        calendarDetailsService.addCalendarDetailsAll(planId, dto, userDetails.getUser().getId());
        return new ResponseEntity<>(PlanResponseDto.ResponseNodata.builder()
                .msg("상세계획 내용 전체등록 완료!")
                .result("success")
                .build(),HttpStatus.OK);
    }
}
