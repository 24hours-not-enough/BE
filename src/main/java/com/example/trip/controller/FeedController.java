package com.example.trip.controller;


import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.dto.FeedResponseDto;
import com.example.trip.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class FeedController {
    private final FeedService feedService;

    @PostMapping("/feed")
    public ResponseEntity<FeedResponseDto.FeedResponseOptional> registerFeed(
            @AuthenticationPrincipal UserDetailsImpl user,
            @Valid @RequestBody FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto) {
        List<Long> feedDetailLocs = feedService.registerFeed(user.getUser(), feedRequestRegisterDto);
        System.out.println("여행 날짜"+feedRequestRegisterDto.getTravelStart());
        return new ResponseEntity<>(FeedResponseDto.FeedResponseOptional.builder()
                .result("success")
                .msg("피드 등록 성공하였습니다.")
                .data(feedDetailLocs)
                .build(), HttpStatus.OK);
    }
    @PostMapping("/feed/image/{feedDetailLocId}")
    public ResponseEntity<FeedResponseDto.FeedResponseOptional> registerFeedImage(
            @PathVariable Long feedDetailLocId,
            @RequestPart(value="imgFiles") List<MultipartFile> imgFiles) {
        Map<String, String> nameAndUrl = feedService.registerFeedImage(feedDetailLocId, imgFiles);
        return new ResponseEntity<>(FeedResponseDto.FeedResponseOptional.builder()
                .result("success")
                .msg("피드 이미지 등록 성공하였습니다.")
                .data(nameAndUrl)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/image")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> deleteFeedImage(
            @RequestBody FeedRequestDto.FeedRequestDeleteImgDto  FeedRequestDeleteImgDto) {
        feedService.deleteFeedImage(FeedRequestDeleteImgDto);
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 이미지 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PutMapping("/feed/{feedId}")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> modifyFeed(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long feedId,
            @RequestBody FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto) {
        feedService.modifyFeed(userDetails.getUser(), feedId, feedRequestModifyDto);
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 수정 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedId}")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> deleteFeed(
            @PathVariable Long feedId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.deleteFeed(userDetails.getUser(), feedId);
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/feed/{feedDetailLocId}/like")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> likeFeed(@PathVariable Long feedDetailLocId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.likeFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 좋아요 등록에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedDetailLocId}/unlike")
    public ResponseEntity<FeedResponseDto.FeedResponseOptional> unlikeFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.unlikeFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.FeedResponseOptional.builder()
                .result("success")
                .msg("피드 좋아요 취소에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/feed/{feedDetailLocId}/bookmark")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> bookmarkFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.bookmarkFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 북마크 등록에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedDetailLocId}/unbookmark")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> unbookmarkFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.unbookmarkFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 북마크 취소에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/feed/comment/{feedDetailLocId}")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> registerFeedComment(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long feedDetailLocId, @RequestBody FeedRequestDto.FeedRequestCommentRegisterDto feedRequestCommentRegisterDto) {
        feedService.registerFeedComment(userDetails.getUser(), feedDetailLocId, feedRequestCommentRegisterDto);
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
        feedService.modifyFeedComment(userDetails.getUser(), commentId, feedRequestCommentModifyDto);
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("댓글 수정 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/comment/{commentId}")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> deleteFeedComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId) {
        feedService.deleteFeedComment(userDetails.getUser(), commentId);
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("댓글 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
}
