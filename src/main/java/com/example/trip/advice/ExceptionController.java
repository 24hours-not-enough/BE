package com.example.trip.advice;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.example.trip.advice.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


@RequiredArgsConstructor
@RestControllerAdvice
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

    @ExceptionHandler(AuthFeedNotFoundException.class)
    public ResponseEntity<Fail> AuthFeedNotFoundException(AuthFeedNotFoundException e) {
        return new ResponseEntity<>(new Fail("권한이 없는 여행 기록입니다."), HttpStatus.OK);
    }

    @ExceptionHandler(FeedDetailLocNotFoundException.class)
    public ResponseEntity<Fail> FeedDetailLocNotFoundException(FeedDetailLocNotFoundException e) {
        return new ResponseEntity<>(new Fail("존재하지 않는 피드 상세 위치입니다."), HttpStatus.OK);
    }

    @ExceptionHandler(FeedNotFoundException.class)
    public ResponseEntity<Fail> FeedFoundException(FeedNotFoundException e) {
        return new ResponseEntity<>(new Fail("존재하지 않는 피드입니다."), HttpStatus.OK);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Fail> MissingServletRequestPartException(MissingServletRequestPartException e) {
        return new ResponseEntity<>(new Fail("등록할 닉네임이 없습니다."), HttpStatus.OK);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<Fail> AmazonS3Exception(AmazonS3Exception e) {
        return new ResponseEntity<>(new Fail("업로드할 파일이 없습니다."), HttpStatus.OK);
    }
}
