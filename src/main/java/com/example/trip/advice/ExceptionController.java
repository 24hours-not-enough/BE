package com.example.trip.advice;

import com.example.trip.advice.Fail;
import com.example.trip.advice.exception.*;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;


@RequiredArgsConstructor
@RestControllerAdvice
@ControllerAdvice
public class ExceptionController {
    // 빈 문자열 (trim 적용) 체크
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Fail> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new Fail("입력값이 빈 문자열입니다."), HttpStatus.OK);
    }

    // null 값인 경우 체크
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Fail> BadRequestException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new Fail("입력 값이 존재하지 않습니다."), HttpStatus.OK);
    }

    // 피드 권한 체크
    @ExceptionHandler(AuthFeedNotFoundException.class)
    public ResponseEntity<Fail> AuthFeedNotFoundException(AuthFeedNotFoundException e) {
        return new ResponseEntity<>(new Fail("피드 수정, 삭제는 본인만 할 수 있습니다."), HttpStatus.OK);
    }

    // 피드 댓글 권한 체크
    @ExceptionHandler(AuthFeedCommentNotFoundException.class)
    public ResponseEntity<Fail> AuthFeedCommentNotFoundException(AuthFeedCommentNotFoundException e) {
        return new ResponseEntity<>(new Fail("피드 댓글 수정, 삭제는 본인만 할 수 있습니다."), HttpStatus.OK);
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<Fail> PlanNotFoundException(PlanNotFoundException e) {
        return new ResponseEntity<>(new Fail("존재하지 않는 계획입니다."), HttpStatus.OK);
    }

    @ExceptionHandler(AuthPlanNotFoundException.class)
    public ResponseEntity<Fail> AuthPlanNotFoundException(AuthPlanNotFoundException e) {
        return new ResponseEntity<>(new Fail("권한이 없는 계획입니다."), HttpStatus.OK);
    }

    @ExceptionHandler(CalendarNotFoundException.class)
    public ResponseEntity<Fail> CalendarNotFoundException(CalendarNotFoundException e) {
        return new ResponseEntity<>(new Fail("존재하지 않는 일정입니다."), HttpStatus.OK);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<Fail> LocationNotFoundException(LocationNotFoundException e) {
        return new ResponseEntity<>(new Fail("존재하지 않는 위치정보 입니다."), HttpStatus.OK);
    }

    @ExceptionHandler(CheckListNotFoundException.class)
    public ResponseEntity<Fail> CheckListNotFoundException(CheckListNotFoundException e) {
        return new ResponseEntity<>(new Fail("존재하지 않는 체크리스트 입니다."), HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Fail> UserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new Fail("닉네임이 존재하지 않습니다."), HttpStatus.OK);
    }
}
