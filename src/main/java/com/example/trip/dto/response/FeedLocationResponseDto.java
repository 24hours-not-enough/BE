package com.example.trip.dto.response;

import com.example.trip.domain.FeedLocation;
import com.example.trip.dto.response.FeedDetailLocResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class FeedLocationResponseDto {


    public static class BookMark {
        private Long placeId;
        private String placeName;
        private String placeAddress;
        private String latitude;
        private String longitude;
        private List<FeedDetailLocResponseDto.GetFeedDetailLoc> feedDetailLoc;


        public BookMark(FeedLocation feedLocation) {
            this.placeId = feedLocation.getId();
            this.placeName = feedLocation.getName();
            this.placeAddress = feedLocation.getPlaceAddress();
            this.latitude = feedLocation.getLatitude();
            this.longitude = feedLocation.getLongitude();
            this.feedDetailLoc = feedLocation.getFeedDetailLocs().stream().map(FeedDetailLocResponseDto.GetFeedDetailLoc::new).collect(Collectors.toList());
        }
    }
}
