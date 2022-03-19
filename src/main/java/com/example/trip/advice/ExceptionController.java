package com.example.trip.advice;

import com.example.trip.advice.Fail;
import com.example.trip.advice.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<Fail> PlanNotFoundException(PlanNotFoundException e) {
        return new ResponseEntity<>(new Fail("존재하지 않는 계획입니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthPlanNotFoundException.class)
    public ResponseEntity<Fail> AuthPlanNotFoundException(AuthPlanNotFoundException e) {
        return new ResponseEntity<>(new Fail("권한이 없는 계획입니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CalendarNotFoundException.class)
    public ResponseEntity<Fail> CalendarNotFoundException(CalendarNotFoundException e) {
        return new ResponseEntity<>(new Fail("존재하지 않는 일정입니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<Fail> LocationNotFoundException(LocationNotFoundException e) {
        return new ResponseEntity<>(new Fail("존재하지 않는 위치정보 입니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckListNotFoundException.class)
    public ResponseEntity<Fail> CheckListNotFoundException(CheckListNotFoundException e) {
        return new ResponseEntity<>(new Fail("존재하지 않는 체크리스트 입니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Fail> UserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new Fail("닉네임이 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CalendarModifyException.class)
    public ResponseEntity<Fail> CalendarModifyException(CalendarModifyException e) {
        return new ResponseEntity<>(new Fail("수정중인 일정입니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckListModifyException.class)
    public ResponseEntity<Fail> CheckListModifyException(CheckListModifyException e) {
        return new ResponseEntity<>(new Fail("수정중인 체크리스트입니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Fail> defaultException(Exception e) {
        return new ResponseEntity<>(new Fail("알수없는 오류입니다. 관리자 문의 부탁드립니다."), HttpStatus.BAD_REQUEST);
    }
}
