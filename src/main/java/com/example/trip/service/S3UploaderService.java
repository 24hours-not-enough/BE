package com.example.trip.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public interface S3UploaderService {
    void upload(MultipartFile multipartFile) throws IOException;
}
