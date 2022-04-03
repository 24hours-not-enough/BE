package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.request.CheckListsRequestDto;
import com.example.trip.dto.response.PlanResponseDto;
import com.example.trip.service.CheckListService;
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
public class CheckListController {

    private final CheckListService checkListService;

    @ApiOperation(value = "체크리스트 등록 및 수정", notes = "계획이 존재해야만 등록 가능")
    @PostMapping("/plan/{planId}/checkLists")
    public ResponseEntity<PlanResponseDto.ResponseNodata> CheckListAdd(@PathVariable Long planId, @RequestBody List<CheckListsRequestDto> dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        checkListService.addCheckList(planId,dto,userDetails.getUser().getId());
        return new ResponseEntity<>(PlanResponseDto.ResponseNodata.builder()
                .msg("체크리스트 등록 완료!")
                .result("success")
                .build(),HttpStatus.OK);
    }


//    @PutMapping("/plan/{planId}/checkLists/{checkListsId}")
//    public ResponseEntity<Success> CheckListModify(@PathVariable Long checkListsId, @RequestBody CheckListsRequestDto dto, @PathVariable Long planId, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        checkListService.modifyCheckList(checkListsId, dto, planId, userDetails.getUser().getId());
//        return new ResponseEntity<>(new Success(true,"체크리스트 수정 완료!"), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/plan/{planId}/checkLists/{checkListsId}")
//    public ResponseEntity<Success> CheckListRemove(@PathVariable Long checkListsId, @PathVariable Long planId, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        checkListService.removeCheckList(checkListsId, planId, userDetails.getUser().getId());
//        return new ResponseEntity<>(new Success(true,"체크리스트 삭제 완료!"), HttpStatus.OK);
//    }

    @PutMapping ("/plan/{planId}/checkLists")
    public ResponseEntity<PlanResponseDto.ResponseNodata> CheckListUnLock(@PathVariable Long planId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        checkListService.addCheckListUnLock(planId, userDetails.getUser().getId());
        return new ResponseEntity<>(PlanResponseDto.ResponseNodata.builder()
                .msg("체크리스트 잠금 해지 완료!")
                .result("success")
                .build(),HttpStatus.OK);
    }

    @ApiOperation(value = "체크리스트 잠금", notes = "계획이 존재해야만 잠금 가능하며 잠금 여부 표시")
    @PutMapping("/plan/{planId}/checkLists/lock")
    public ResponseEntity<PlanResponseDto.ResponseNodata> CheckListLock(@PathVariable Long planId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        checkListService.addCheckListLock(planId, userDetails.getUser().getId());
        return new ResponseEntity<>(PlanResponseDto.ResponseNodata.builder()
                .msg("체크리스트 잠금 완료!")
                .result("success")
                .build(),HttpStatus.OK);
    }
}
