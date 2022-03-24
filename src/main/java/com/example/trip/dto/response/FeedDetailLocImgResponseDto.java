package com.example.trip.dto.response;

import com.example.trip.domain.FeedDetailLocImg;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class FeedDetailLocImgResponseDto {

    @AllArgsConstructor
    @Getter
    public static class ImgUrl {
        private String imgUrl;

        public ImgUrl(FeedDetailLocImg feedDetailLocImg) {
            this.imgUrl = feedDetailLocImg.getImgUrl();
        }
    }
}
