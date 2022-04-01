package com.example.trip.dto.request;

import com.example.trip.domain.FeedDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;


public class FeedRequestDto {

    @Getter
    public static class FeedRequestRegisterDto {
        @NotBlank
        private String title;

        //        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime travelStart;

        //        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime travelEnd;

        private List<FeedDetail> feedDetail;
    }

    @Getter
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
    public static class FeedRequestCommentRegisterDto {
        @NotBlank
        private String content;
    }

    @Getter
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
        private Long leftX;

        @NotBlank
        private Long rightX;

        @NotBlank
        private Long topY;

        @NotBlank
        private Long bottomY;

    }
}
