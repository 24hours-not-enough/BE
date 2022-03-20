package com.example.trip.controller;

import com.example.trip.advice.*;
import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.domain.Image;
import com.example.trip.domain.Role;
import com.example.trip.domain.User;
import com.example.trip.dto.request.PlanRequestDto;
import com.example.trip.dto.response.PlanResponseDto;
import com.example.trip.repository.UserRepository;
import com.example.trip.service.PlanService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlanController {

    private final PlanService planService;

    private final UserRepository userRepository;

    @ApiOperation(value = "계획 등록", notes = "로그인 사용자만 가능")
    @PostMapping("/plan")
    public ResponseEntity<PlanResponseDto.Response> planAdd(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PlanRequestDto.Regist Regist) {
        Long planId = planService.addPlan(userDetails.getUser().getId(),Regist);
        return new ResponseEntity<>(PlanResponseDto.Response.builder()
                .result("success")
                .msg("계획 등록 성공!")
                .data(planId)
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "나의 여행계획 전체조회", notes = "로그인 사용자만 가능")
    @GetMapping("/plan")
    public ResponseEntity<PlanResponseDto.Response> planList(@AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 5) Pageable pageable) {
        List<PlanResponseDto> plan = planService.findPlan(userDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(PlanResponseDto.Response.builder()
                .result("success")
                .msg("나의 여행계획 전체 조회입니다.")
                .data(plan)
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "여행 계획 수정, 복구, 삭제", notes = "계획을 등록한 사람만 가능")
    @PutMapping("/plan/{planId}")
    public ResponseEntity<PlanResponseDto.Response> planModify(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId, @RequestBody PlanRequestDto.Modify modify) {
        planService.modifyPlan(userDetails.getUser().getId(), planId, modify);
        String msg;
        if(modify.getTitle()!=null){
            msg = "계획 수정 성공!";
        }
        else if(modify.getDel_fl()==true){
            msg = "계획 복구 성공!";
        }else{
            msg = "계획 삭제 성공!";
        }

        return new ResponseEntity<>(PlanResponseDto.Response.builder()
                .result("success")
                .msg(msg)
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "나의 여행계획 단건 조회", notes = "로그인 사용자만 가능")
    @GetMapping("/plan/{planId}")
    public ResponseEntity<PlanResponseDto.Response> planOne(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId) {
        PlanResponseDto planOne = planService.findPlanOne(userDetails.getUser().getId(), planId);
        return new ResponseEntity<>(PlanResponseDto.Response.builder()
                .result("success")
                .msg("계획 단건 조회 성공!")
                .data(planOne)
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "나의 여행계획 영구 삭제", notes = "로그인 사용자만 가능")
    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<PlanResponseDto.Response> planModify(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId) {
        planService.removePlan(userDetails.getUser().getId(), planId);
        return new ResponseEntity<>(PlanResponseDto.Response.builder()
                .result("success")
                .msg("계획 영구 삭제 성공!")
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "나의 여행계획 나가기", notes = "로그인 사용자만 가능")
    @DeleteMapping("/plan/{planId}/member")
    public ResponseEntity<PlanResponseDto.Response> planMemberRemove(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId){
        planService.removePlanMember(userDetails.getUser().getId(),planId);
        return new ResponseEntity<>(PlanResponseDto.Response.builder()
                .result("success")
                .msg("계획 나가기 성공!")
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "나의 여행계획 전체조회", notes = "로그인 사용자만 가능")
    @GetMapping("/plan/{planId}/planDetails")
    public ResponseEntity<PlanResponseDto.Response> MemberAndPlanAllList(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId){
        List<PlanResponseDto.DetailAll> planAllAndMember = planService.findPlanAllAndMember(userDetails.getUser().getId(), planId);
        return new ResponseEntity<>(PlanResponseDto.Response.builder()
                .result("success")
                .msg("계획 상세 전체조회 성공")
                .data(planAllAndMember)
                .build(),HttpStatus.OK);
    }
}
