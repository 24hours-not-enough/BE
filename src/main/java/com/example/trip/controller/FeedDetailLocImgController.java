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

    @Caching(evict = {
            @CacheEvict(value = "feedlist"),
            @CacheEvict(value = "feed", key = "#feedId"),
            @CacheEvict(value = "feeddetailloc", key = "#feeddetaillocId")
    })
    @PostMapping("/feed/image/{feedDetailLocId}")
    public ResponseEntity<FeedResponseDto.FeedResponseOptional> registerFeedImage(
            @PathVariable Long feedDetailLocId,
            @RequestPart(value="imgFiles") List<MultipartFile> imgFiles) {
        Map<String, String> nameAndUrl = feedDetailLocImgService.registerFeedImage(feedDetailLocId, imgFiles);
        return new ResponseEntity<>(FeedResponseDto.FeedResponseOptional.builder()
                .result("success")
                .msg("피드 이미지 등록 성공하였습니다.")
                .data(nameAndUrl)
                .build(), HttpStatus.OK);
    }

    @Caching(evict = {
            @CacheEvict(value = "feedlist"),
            @CacheEvict(value = "feed", key = "#feedId"),
            @CacheEvict(value = "feeddetailloc", key = "#feeddetaillocId")
    })
    @DeleteMapping("/feed/image")
    public ResponseEntity<FeedResponseDto.FeedResponseDefault> deleteFeedImage(
            @RequestBody FeedRequestDto.FeedRequestDeleteImgDto  FeedRequestDeleteImgDto) {
        feedDetailLocImgService.deleteFeedImage(FeedRequestDeleteImgDto);
        return new ResponseEntity<>(FeedResponseDto.FeedResponseDefault.builder()
                .result("success")
                .msg("피드 이미지 삭제 성공하였습니다.")
                .build(), HttpStatus.OK);
    }
}
