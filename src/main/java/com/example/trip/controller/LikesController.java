package com.example.trip.controller;


import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.service.LikesService;
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
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/feed/{feedDetailLocId}/like")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> likeFeed(@PathVariable Long feedDetailLocId,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 좋아요를 등록하는 함수 호출
        likesService.likeFeed(feedDetailLocId, userDetails.getUser());
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 좋아요 등록에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedDetailLocId}/unlike")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> unlikeFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //좋아요를 취소하는 함수 호출
        likesService.unlikeFeed(feedDetailLocId, userDetails.getUser());
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 좋아요 취소에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
}

