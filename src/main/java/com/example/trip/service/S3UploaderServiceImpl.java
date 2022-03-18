package com.example.trip.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;


@Slf4j
@RequiredArgsConstructor
@Component
public class S3UploaderServiceImpl implements S3UploaderService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public void upload(MultipartFile multipartFile) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
        metadata.setContentLength(multipartFile.getSize());

        try (final InputStream uploadImageFileInputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket,
                    multipartFile.getOriginalFilename(),
                    uploadImageFileInputStream,
                    metadata));
            System.out.println(amazonS3Client.getUrl(bucket, multipartFile.getOriginalFilename()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
