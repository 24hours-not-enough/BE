package com.example.trip.advice;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.example.trip.advice.exception.*;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
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
        return new ResponseEntity<>(new Fail("입력값이 빈 문자열입니다."), HttpStatus.BAD_REQUEST);
    }

    // null 값인 경우 체크
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Fail> BadRequestException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new Fail("입력 값이 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
    }

    // 피드 권한 체크
    @ExceptionHandler(AuthFeedNotFoundException.class)
    public ResponseEntity<Fail> AuthFeedNotFoundException(AuthFeedNotFoundException e) {
        return new ResponseEntity<>(new Fail("피드 수정, 삭제는 본인만 할 수 있습니다."), HttpStatus.BAD_REQUEST);
    }
    // 피드 좋아요 권한 체크
    @ExceptionHandler(AuthLikesNotFoundException.class)
    public ResponseEntity<Fail> AuthLikesNotFoundException(AuthLikesNotFoundException e) {
        return new ResponseEntity<>(new Fail("피드 좋아요 수정, 삭제는 본인만 할 수 있습니다."), HttpStatus.BAD_REQUEST);
    }
    // 피드 좋아요 이미 했는지 체크
    @ExceptionHandler(AlreadyLikeException.class)
    public ResponseEntity<Fail> AlreadyLikeException(AlreadyLikeException e) {
        return new ResponseEntity<>(new Fail("해당 피드는 이미 좋아요를 했습니다."), HttpStatus.BAD_REQUEST);
    }
    // 피드 북마크 권한 체크
    @ExceptionHandler(AuthBookMarkNotFoundException.class)
    public ResponseEntity<Fail> AuthBookMarkNotFoundException(AuthBookMarkNotFoundException e) {
        return new ResponseEntity<>(new Fail("피드 북마크 수정, 삭제는 본인만 할 수 있습니다."), HttpStatus.BAD_REQUEST);
    }

    // 피드 댓글 권한 체크
    @ExceptionHandler(AuthFeedCommentNotFoundException.class)
    public ResponseEntity<Fail> AuthFeedCommentNotFoundException(AuthFeedCommentNotFoundException e) {
        return new ResponseEntity<>(new Fail("피드 댓글 수정, 삭제는 본인만 할 수 있습니다."), HttpStatus.BAD_REQUEST);
    }
    // 피드 존재 체크
    @ExceptionHandler(FeedNotFoundException.class)
    public ResponseEntity<Fail> FeedNotFoundException(FeedNotFoundException e) {
        return new ResponseEntity<>(new Fail("해당 피드 값이 없습니다."), HttpStatus.BAD_REQUEST);
    }

    // 피드 상세 위치 존재 체크
    @ExceptionHandler(FeedDetailLocNotFoundException.class)
    public ResponseEntity<Fail> FeedDetailLocNotFoundException(FeedDetailLocNotFoundException e) {
        return new ResponseEntity<>(new Fail("해당 피드 상세 위치 값이 없습니다."), HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Fail> MissingServletRequestPartException(MissingServletRequestPartException e) {
        return new ResponseEntity<>(new Fail("등록할 닉네임이 없습니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<Fail> AmazonS3Exception(AmazonS3Exception e) {
        return new ResponseEntity<>(new Fail("업로드할 파일이 없습니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistUsernameException.class)
    public ResponseEntity<Fail> AlreadyExistUsernameException(AlreadyExistUsernameException e) {
        return new ResponseEntity<>(new Fail("중복된 유저명입니다."), HttpStatus.BAD_REQUEST);
    }


}
