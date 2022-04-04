package com.example.trip.advice.validator;

import com.example.trip.advice.exception.TitleNotFoundException;
import com.example.trip.dto.request.FeedRequestDto;
import org.springframework.stereotype.Component;

@Component
public class FeedValidator {

    public static void validateFeedRegisterInput(FeedRequestDto.FeedRequestRegisterDto registerDto) {
        // @NotBlank랑 겹치는 부분 논의하기
        if (registerDto.getTitle() == null) {
            throw new TitleNotFoundException();
        }

        if (registerDto.getTravelStart() == null) {

        }

        if (registerDto.getTravelEnd() == null) {

        }

        if (registerDto.getTravelStart().isAfter(registerDto.getTravelEnd())) {

        }

        if (registerDto.getTravelEnd().isBefore(registerDto.getTravelStart())) {

        }

        if (registerDto.getFeedDetail() == null) {

        }
    }

    public static void validateFeedModifyInput(FeedRequestDto.FeedRequestModifyDto modifyDto) {
        // @NotBlank랑 겹치는 부분 논의하기
        if (modifyDto.getTitle() == null) {
            throw new TitleNotFoundException();
        }

        if (modifyDto.getTravelStart() == null) {

        }

        if (modifyDto.getTravelEnd() == null) {

        }

        if (modifyDto.getTravelStart().isAfter(modifyDto.getTravelEnd())) {

        }

        if (modifyDto.getTravelEnd().isBefore(modifyDto.getTravelStart())) {

        }

        if (modifyDto.getFeedDetail() == null) {

        }

    }

    public static void validateCommentRegisterInput(FeedRequestDto.FeedRequestCommentRegisterDto registerDto) {
        // @NotBlank랑 겹치는 부분 논의하기
        if (registerDto.getContent() == null) {

        }
    }

    public static void validateCommentModifyInput(FeedRequestDto.FeedRequestCommentModifyDto modifyDto) {
        // @NotBlank랑 겹치는 부분 논의하기
        if (modifyDto.getContent() == null) {

        }
    }

    public static void validateImgDeleteInput(FeedRequestDto.FeedRequestDeleteImgDto deleteDto) {
        if (deleteDto.getFileNames() == null) {

        }
    }
}
