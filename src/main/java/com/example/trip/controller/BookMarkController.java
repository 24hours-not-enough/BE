package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class BookMarkController {
    private final BookMarkService bookmarkService;

    @PostMapping("/feed/{feedDetailLocId}/bookmark")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> bookmarkFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        bookmarkService.bookmarkFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 북마크 등록에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedDetailLocId}/unbookmark")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> unbookmarkFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        bookmarkService.unbookmarkFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 북마크 취소에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
}
