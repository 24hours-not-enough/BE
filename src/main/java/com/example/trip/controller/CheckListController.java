package com.example.trip.controller;

import com.example.trip.advice.Success;
import com.example.trip.dto.request.CheckListsRequestDto;
import com.example.trip.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CheckListController {

    private final CheckListService checkListService;

    @PostMapping("/plan/{planId}/checkLists")
    public ResponseEntity<Success> CheckListAdd(@PathVariable Long planId, @RequestBody CheckListsRequestDto dto){
        checkListService.addCheckList(planId,dto);
        return new ResponseEntity<>(new Success(true,"체크리스트 등록 완료!"), HttpStatus.OK);
    }
}
