package com.example.trip.controller;

import com.example.trip.advice.Success;
import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.request.CheckListsRequestDto;
import com.example.trip.service.CheckListService;
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

    @PostMapping("/plan/{planId}/checkLists")
    public ResponseEntity<Success> CheckListAdd(@PathVariable Long planId, @RequestBody List<CheckListsRequestDto> dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        checkListService.addCheckList(planId,dto,userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true,"체크리스트 등록 완료!"), HttpStatus.OK);
    }


    @PutMapping("/plan/{planId}/checkLists/{checkListsId}")
    public ResponseEntity<Success> CheckListModify(@PathVariable Long checkListsId, @RequestBody CheckListsRequestDto dto, @PathVariable Long planId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        checkListService.modifyCheckList(checkListsId, dto, planId, userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true,"체크리스트 수정 완료!"), HttpStatus.OK);
    }

    @DeleteMapping("/plan/{planId}/checkLists/{checkListsId}")
    public ResponseEntity<Success> CheckListRemove(@PathVariable Long checkListsId, @PathVariable Long planId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        checkListService.removeCheckList(checkListsId, planId, userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true,"체크리스트 삭제 완료!"), HttpStatus.OK);
    }

    @PutMapping("/plan/{planId}/checkLists")
    public ResponseEntity<Success> CheckListLock(@PathVariable Long planId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        checkListService.addCheckListLock(planId, userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true,"체크리스트 잠금 완료!"), HttpStatus.OK);
    }
}
