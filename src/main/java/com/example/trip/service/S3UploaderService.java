package com.example.trip.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface S3UploaderService {
    Map<String, String> upload(MultipartFile multipartFile) throws IOException;
}
