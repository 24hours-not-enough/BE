package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.service.FeedCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class FeedCommentController {
    private final FeedCommentService feedCommentService;

    @PostMapping("/feed/comment/{feedDetailLocId}")
    public ResponseEntity<FeedResponseDto.FeedResponseOptional> registerFeedComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long feedDetailLocId, @RequestBody FeedRequestDto.FeedRequestCommentRegisterDto feedRequestCommentRegisterDto) {
        //서비스단 댓글 등록 함수 호출
        Long commentId = feedCommentService.registerFeedComment(userDetails.getUser(), feedDetailLocId, feedRequestCommentRegisterDto);
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseOptional.builder()
                .result("success")
                .msg("댓글 등록 성공하였습니다.")
                .data(commentId)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/feed/comment/{commentId}")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> modifyFeedComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId,
            @RequestBody FeedRequestDto.FeedRequestCommentModifyDto feedRequestCommentModifyDto) {
        //서비스단 댓글 수정 함수 호출
        feedCommentService.modifyFeedComment(userDetails.getUser(), commentId, feedRequestCommentModifyDto);
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("댓글 수정 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/comment/{commentId}")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> deleteFeedComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId) {
        feedCommentService.deleteFeedComment(userDetails.getUser(), commentId);
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("댓글 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
}

