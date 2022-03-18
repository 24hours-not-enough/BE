package com.example.trip.controller;


import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.dto.FeedRequestDto;
import com.example.trip.dto.FeedResponseDto;
import com.example.trip.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class FeedController {
    private final FeedService feedService;

    @PostMapping("/feed")
    public ResponseEntity<FeedResponseDto> registerFeed(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto) {
        List<FeedDetailLoc> feedDetailLocs = feedService.registerFeed(user.getUser(), feedRequestRegisterDto);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 등록 성공하였습니다.")
                .data(feedDetailLocs)
                .build(), HttpStatus.OK);


    }
    @PostMapping("/feed/image/{feedDetailLocId}")
    public ResponseEntity<FeedResponseDto> registerFeedImage(
            @PathVariable Long feedDetailLocId,
            @RequestPart(value="imgFiles") List<MultipartFile> imgFiles) {
        Map<String, String> nameAndUrl = feedService.registerFeedImage(feedDetailLocId, imgFiles);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 이미지 등록 성공하였습니다.")
                .data(nameAndUrl)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/image")
    public ResponseEntity<FeedResponseDto> deleteFeedImage(
            @RequestBody FeedRequestDto.FeedRequestDeleteImgDto  FeedRequestDeleteImgDto) {
        feedService.deleteFeedImage(FeedRequestDeleteImgDto);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 이미지 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/feed/{feedId}")
    public ResponseEntity<FeedResponseDto> modifyFeed(@PathVariable Long feedId, @RequestBody FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto) {
        feedService.modifyFeed(feedId, feedRequestModifyDto);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 수정 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedId}")
    public ResponseEntity<FeedResponseDto> deleteFeed(@PathVariable Long feedId) {
        feedService.deleteFeed(feedId);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/feed/{feedDetailLocId}/like")
    public ResponseEntity<FeedResponseDto> likeFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.likeFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 좋아요 등록에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedDetailLocId}/unlike")
    public ResponseEntity<FeedResponseDto> unlikeFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.unlikeFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 좋아요 취소에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/feed/{feedDetailLocId}/bookmark")
    public ResponseEntity<FeedResponseDto> bookmarkFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.bookmarkFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 북마크 등록에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedDetailLocId}/unbookmark")
    public ResponseEntity<FeedResponseDto> unbookmarkFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.unbookmarkFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("피드 북마크 취소에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/feed/comment/{feedDetailLocId}")
    public ResponseEntity<FeedResponseDto> registerFeedComment(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long feedDetailLocId, @RequestBody FeedRequestDto.FeedRequestCommentRegisterDto feedRequestCommentRegisterDto) {
        feedService.registerFeedComment(userDetails.getUser(), feedDetailLocId, feedRequestCommentRegisterDto);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("댓글 등록 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/feed/comment/{commentId}")
    public ResponseEntity<FeedResponseDto> modifyFeedComment(@PathVariable Long commentId, @RequestBody FeedRequestDto.FeedRequestCommentModifyDto feedRequestCommentModifyDto) {
        feedService.modifyFeedComment(commentId, feedRequestCommentModifyDto);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("댓글 수정 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/comment/{commentId}")
    public ResponseEntity<FeedResponseDto> deleteFeedComment(@PathVariable Long commentId) {
        feedService.deleteFeedComment(commentId);
        return new ResponseEntity<>(FeedResponseDto.builder()
                .result("success")
                .msg("댓글 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
}
