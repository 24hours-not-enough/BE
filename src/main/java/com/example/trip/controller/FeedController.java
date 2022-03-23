package com.example.trip.controller;


import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.dto.FeedResponseDto;
import com.example.trip.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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
}
