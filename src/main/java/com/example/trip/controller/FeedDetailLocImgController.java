package com.example.trip.controller;

import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.service.FeedDetailLocImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class FeedDetailLocImgController {
    private final FeedDetailLocImgService feedDetailLocImgService;

    @PostMapping("/feed/image")
    public ResponseEntity<FeedResponseDto.FeedResponseOptional> registerFeedImage(
            @RequestPart(value="imgFiles") List<MultipartFile> imgFiles) {
        // 피드별 이미지에 관한 정보를 저장하는 함수 호출
        Map<String, String> nameAndUrl = feedDetailLocImgService.registerFeedImage(imgFiles);
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseOptional.builder()
                .result("success")
                .msg("피드 이미지 등록 성공하였습니다.")
                .data(nameAndUrl)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/feed/image")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> deleteFeedImage(
            @RequestBody FeedRequestDto.FeedRequestDeleteImgDto  FeedRequestDeleteImgDto) {
        // 피드별 이미지에 관한 정보를 저장하는 함수 호출
        feedDetailLocImgService.deleteFeedImage(FeedRequestDeleteImgDto);
        //약속한 리스펀스 엔티티 형태로 만들어서 리턴
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 이미지 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
}

