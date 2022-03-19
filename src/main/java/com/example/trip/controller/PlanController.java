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

    @PostMapping("/plan")
    public ResponseEntity<GetPlan> planAdd(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PlanRequestDto.Regist registDto) {
        System.out.println("userDetails = " + userDetails.getUser().getId());
        Long planId = planService.addPlan(userDetails.getUser().getId(),registDto);
        return new ResponseEntity<>(new GetPlan(true, "계획 등록 성공!", planId), HttpStatus.OK);
    }

    @GetMapping("/plan")
    public ResponseEntity<GetAllPlan> planList(@AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size = 5) Pageable pageable) {
        List<PlanResponseDto> plan = planService.findPlan(userDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new GetAllPlan(true, "나의 여행계획 전체 조회입니다.", plan), HttpStatus.OK);
    }

    @PutMapping("/plan/{planId}")
    public ResponseEntity<Success> planModify(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId, @RequestBody PlanRequestDto.Modify modify) {
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

        return new ResponseEntity<>(new Success(true, msg), HttpStatus.OK);
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<GetPlanOne> planOne(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId) {
        PlanResponseDto planOne = planService.findPlanOne(userDetails.getUser().getId(), planId);
        return new ResponseEntity<>(new GetPlanOne(true, "계획 단건 조회 성공!",planOne), HttpStatus.OK);
    }

    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<Success> planModify(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId) {
        planService.removePlan(userDetails.getUser().getId(), planId);
        return new ResponseEntity<>(new Success(true, "계획 영구 삭제 성공!"), HttpStatus.OK);
    }

    @DeleteMapping("/plan/{planId}/member")
    public ResponseEntity<Success> planMemberRemove(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId){
        planService.removePlanMember(userDetails.getUser().getId(),planId);
        return new ResponseEntity<>(new Success(true, "계획 나가기 성공!"), HttpStatus.OK);
    }

    @GetMapping("/plan/{planId}/planDetails")
    public ResponseEntity<GetAllPlanDetails> MemberAndPlanAllList(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long planId){
        List<PlanResponseDto.DetailAll> planAllAndMember = planService.findPlanAllAndMember(userDetails.getUser().getId(), planId);
        return new ResponseEntity<>(new GetAllPlanDetails(true, "계획 상세 전체조회 성공",planAllAndMember), HttpStatus.OK);
    }
}
