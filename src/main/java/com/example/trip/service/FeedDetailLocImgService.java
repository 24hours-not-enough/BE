package com.example.trip.service;

import com.example.trip.advice.exception.FeedDetailLocNotFoundException;
import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.FeedDetailLocImg;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.repository.FeedDetailLocImgRepository;
import com.example.trip.repository.FeedDetailLocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FeedDetailLocImgService {
    private final FeedDetailLocRepository feedDetailLocRepository;
    private final FeedDetailLocImgRepository feedDetailLocImgRepository;
    private final AwsS3Service awsS3Service;

    public Map<String, String> registerFeedImage(Long feedDetailLocId, List<MultipartFile> imgFiles) {
        Map<String, String> nameAndUrl = awsS3Service.uploadFile(imgFiles);
        // 해당 피드 상세 위치 값이 있는지 체크
        FeedDetailLoc feedDetailLoc = feedDetailLocRepository.findById(feedDetailLocId)
                .orElseThrow(() -> new FeedDetailLocNotFoundException());
        nameAndUrl.entrySet().forEach(x -> feedDetailLocImgRepository.save(FeedDetailLocImg.builder()
                .feedDetailLoc(feedDetailLoc)
                .fileName(x.getKey())
                .imgUrl(x.getValue())
                .build()));

        return nameAndUrl;
    }

    public void deleteFeedImage(FeedRequestDto.FeedRequestDeleteImgDto FeedRequestDeleteImgDto) {
        List<String> fileNames = FeedRequestDeleteImgDto.getFileNames();
        fileNames.forEach(x -> awsS3Service.deleteFile(x));
    }
}
