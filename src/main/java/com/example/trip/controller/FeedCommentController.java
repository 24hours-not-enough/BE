package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.FeedResponseDto;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.service.FeedCommentService;
import com.example.trip.service.FeedService;
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
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> registerFeedComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long feedDetailLocId, @RequestBody FeedRequestDto.FeedRequestCommentRegisterDto feedRequestCommentRegisterDto) {
        feedCommentService.registerFeedComment(userDetails.getUser(), feedDetailLocId, feedRequestCommentRegisterDto);
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("댓글 등록 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/feed/comment/{commentId}")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> modifyFeedComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId,
            @RequestBody FeedRequestDto.FeedRequestCommentModifyDto feedRequestCommentModifyDto) {
        feedCommentService.modifyFeedComment(userDetails.getUser(), commentId, feedRequestCommentModifyDto);
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
