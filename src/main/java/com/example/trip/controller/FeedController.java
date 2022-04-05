package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.response.FeedDetailLocResponseDto.GetFeedDetailLoc;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        //서비스단 피드 등록 함수 호출
        List<Long> feedDetailLocs = feedService.registerFeed(user.getUser(), feedRequestRegisterDto);
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
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
        //서비스단 피드 수정 함수 호출
        feedService.modifyFeed(userDetails.getUser(), feedId, feedRequestModifyDto);
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 수정 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
    @DeleteMapping("/feed/{feedId}")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> deleteFeed(
            @PathVariable Long feedId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //서비스단 피드 삭제 함수 호출
        feedService.deleteFeed(userDetails.getUser(), feedId);
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedResponseDto.Response> feedInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<FeedResponseDto.GetFeed> feeds = feedService.getFeeds(userDetails .getUser().getId());
        List<Map<String, List<GetFeedDetailLoc>>> likes = feedService.getLikeFeeds(userDetails.getUser().getId());
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.Response.builder()
                .result("success")
                .msg("피드 정보입니다.")
                .myFeeds(feeds)
                .myLikes(likes).build()
                , HttpStatus.OK);
    }
}

