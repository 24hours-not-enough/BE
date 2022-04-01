package com.example.trip.service;

import com.example.trip.dto.request.FeedRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FeedDetailLocImgService {
    Map<String, String> registerFeedImage(List<MultipartFile> imgFiles);
    void deleteFeedImage(FeedRequestDto.FeedRequestDeleteImgDto FeedRequestDeleteImgDto);
}
