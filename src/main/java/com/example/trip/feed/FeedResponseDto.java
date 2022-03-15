package com.example.trip.feed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedResponseDto {

    @Getter
    @Setter
    public static class FeedResponseRegisterDto{
        private String result;
        private String msg;
        public FeedResponseRegisterDto(String result, String msg) {
            this.result = result;
            this.msg = msg;
        }
    }

    @Getter
    @Setter
    public static class FeedResponseModifyDto{}

    @Getter
    @Setter
    public static class FeedResponseDeleteDto{}


}
