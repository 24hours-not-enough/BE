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

//    @PostConstruct
//    public void initialize() {
//        Image image = new Image("data:image/jpeg;base64,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","카카오","jpg",3);
//        User user1 = User.builder()
//                .email("bbbb@google.com")
//                .socialaccountId("1L")
//                .password("aa")
//                .memberstatus(true)
//                .role(Role.USER)
//                .username("제이")
//                .refreshToken("fdas")
//                .image(image)
//                .build();
//
//        User user2 = User.builder()
//                .email("cccc@google.com")
//                .socialaccountId("2L")
//                .password("aa")
//                .memberstatus(true)
//                .role(Role.USER)
//                .username("깃허브")
//                .refreshToken("fdas")
//                .image(image)
//                .build();
//
//        User user3 = User.builder()
//                .email("dddd@google.com")
//                .socialaccountId("3L")
//                .password("aa")
//                .memberstatus(true)
//                .role(Role.USER)
//                .username("홍길동")
//                .refreshToken("fdas")
//                .image(image)
//                .build();
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
//    }
}
