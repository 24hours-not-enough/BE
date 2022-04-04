package com.example.trip.service.impl;

import com.example.trip.dto.request.FeedRequestDto;
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
    private final S3UploaderServiceImpl s3UploaderService;

    @Override
    @Transactional
    public Map<String, String> registerFeedImage(List<MultipartFile> imgFiles) {
        //s3에 이미지 파일 업로드
        return s3UploaderService.uploadFile(imgFiles);
    }

    @Override
    @Transactional
    public void deleteFeedImage(FeedRequestDto.FeedRequestDeleteImgDto FeedRequestDeleteImgDto) {
        List<String> fileNames = FeedRequestDeleteImgDto.getFileNames();
        // 파일 이름으로 삭제
        fileNames.forEach(x -> s3UploaderService.deleteFile(x));
    }
}
