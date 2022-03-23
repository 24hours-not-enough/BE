package com.example.trip.controller;


import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.FeedResponseDto;
import com.example.trip.service.FeedService;
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
        likesService.likeFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 좋아요 등록에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedDetailLocId}/unlike")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> unlikeFeed(@PathVariable Long feedDetailLocId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.unlikeFeed(feedDetailLocId, userDetails.getUser());
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 좋아요 취소에 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
}
