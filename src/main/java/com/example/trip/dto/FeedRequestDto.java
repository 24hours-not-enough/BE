package com.example.trip.dto;

import com.example.trip.domain.FeedDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.intellij.lang.annotations.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


public class FeedRequestDto {

    @Getter
        public static class FeedRequestRegisterDto{
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
    public static class FeedRequestModifyDto{
        private String title;

        private LocalDateTime travelStart;

        private LocalDateTime travelEnd;

        private List<FeedDetail> feedDetail;
    }

    @Getter
    public static class FeedRequestCommentRegisterDto{
        private String content;
    }

    @Getter
    public static class FeedRequestCommentModifyDto{
        private String content;
    }
    @Getter
    public static class FeedRequestDeleteImgDto{
        private List<String> fileNames;
    }
}
