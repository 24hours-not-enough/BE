package com.example.trip.dto.request;

import com.example.trip.domain.FeedDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;


public class FeedRequestDto {

    @Getter
    @AllArgsConstructor
    public static class FeedRequestRegisterDto {
        @NotBlank
        private String title;

        //ResponseBody에서는 JsonFormat을 사용, @DateTimeForamt은 스프링의 어노테이션. 따라서 Jackson 라이브러리를 활용
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime travelStart;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime travelEnd;

        private List<FeedDetail> feedDetail;
    }

    @Getter
    @AllArgsConstructor
    public static class FeedRequestModifyDto {
        @NotBlank
        private String title;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime travelStart;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime travelEnd;

        @NotBlank
        private List<FeedDetail> feedDetail;
    }

    @Getter
    @AllArgsConstructor
    public static class FeedRequestCommentRegisterDto {
        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class FeedRequestCommentModifyDto {
        @NotBlank
        private String content;
    }

    @Getter
    public static class FeedRequestDeleteImgDto {
        @NotBlank
        private List<String> fileNames;
    }

    @Getter
    public static class FeedRequestMainGetDto {
        @NotBlank
        private double leftX;

        @NotBlank
        private double rightX;

        @NotBlank
        private double topY;

        @NotBlank
        private double bottomY;

    }
}


