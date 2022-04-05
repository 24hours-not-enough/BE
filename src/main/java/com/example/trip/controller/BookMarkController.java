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

    @PostMapping("/feed/{feedLocId}/bookmark")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> bookmarkFeed(@PathVariable Long feedLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //서비스단 북마크 등록 함수 호출
        bookmarkService.bookmarkFeed(feedLocId, userDetails.getUser());
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 북마크 등록에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedLocId}/unbookmark")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> unbookmarkFeed(@PathVariable Long feedLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //서비스단 북마크 해제 함수 호출
        bookmarkService.unbookmarkFeed(feedLocId, userDetails.getUser());
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 북마크 취소에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
}

