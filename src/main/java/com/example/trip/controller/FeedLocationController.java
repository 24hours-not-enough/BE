package com.example.trip.controller;

import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.domain.FeedLocation;
import com.example.trip.dto.request.NewFeedLocationDto;
import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.service.BookMarkService;
import com.example.trip.service.FeedLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class FeedLocationController {
    private final FeedLocationService feedLocationService;

    @PostMapping("/newFeed")
    public ResponseEntity<FeedResponseDto.FeedResponseOptional> newFeedLocation(@RequestBody NewFeedLocationDto newFeedLocationDto) {
        //서비스단 피드 로케이션 등록 함수 호출
        Long feedLocationId = feedLocationService.registerNewFeedLocation(newFeedLocationDto);
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseOptional.builder()
                .result("success")
                .msg("지역 정보 등록에 성공하였습니다.")
                .data(feedLocationId)
                .build(), HttpStatus.OK);
    }
}
