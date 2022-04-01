package com.example.trip.service.impl;

import com.example.trip.advice.exception.FeedDetailLocNotFoundException;
import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.FeedDetailLocImg;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.repository.FeedDetailLocImgRepository;
import com.example.trip.repository.FeedDetailLocRepository;
import com.example.trip.service.AwsS3Service;
import com.example.trip.service.FeedDetailLocImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FeedDetailLocImgServiceImpl implements FeedDetailLocImgService {

    private final FeedDetailLocRepository feedDetailLocRepository;
    private final FeedDetailLocImgRepository feedDetailLocImgRepository;
    private final AwsS3Service awsS3Service;

    @Override
    @Transactional
    public Map<String, String> registerFeedImage(List<MultipartFile> imgFiles) {
        //s3에 이미지 파일 업로드
        Map<String, String> nameAndUrl = awsS3Service.uploadFile(imgFiles);
//        // 해당 피드 상세 위치 값이 있는지 체크
//        FeedDetailLoc feedDetailLoc = feedDetailLocRepository.findById(feedDetailLocId)
//                .orElseThrow(() -> new FeedDetailLocNotFoundException());
//        //FeedDetailLocImg 에 관련 정보 담아서 저장
//        nameAndUrl.entrySet().forEach(x -> feedDetailLocImgRepository.save(FeedDetailLocImg.builder()
//                .feedDetailLoc(feedDetailLoc)
//                .fileName(x.getKey())
//                .imgUrl(x.getValue())
//                .build()));

        return nameAndUrl;
    }

    @Override
    @Transactional
    public void deleteFeedImage(FeedRequestDto.FeedRequestDeleteImgDto FeedRequestDeleteImgDto) {
        List<String> fileNames = FeedRequestDeleteImgDto.getFileNames();
        // 파일 이름으로 삭제
        fileNames.forEach(x -> awsS3Service.deleteFile(x));
    }
}
