package com.example.trip.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedLocationTest {

    @Test
    @DisplayName("위치생성 - 정상케이스")
    void createFeedLocation_normal() {
        //given
        String name = "롯데월드";
        Double latitude = 1050.0;
        Double longitude = 2000.0;
        String placeAddress = "서울특별시 잠실";

        //when
        FeedLocation feedLocation = FeedLocation.builder()
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .placeAddress(placeAddress)
                .build();

        //then
        assertNull(feedLocation.getId());
        assertNull(feedLocation.getFeedDetailLocs());
        assertNull(feedLocation.getBookMarks());
        assertEquals(name, feedLocation.getName());
        assertEquals(latitude, feedLocation.getLatitude());
        assertEquals(longitude, feedLocation.getLongitude());
        assertEquals(placeAddress, feedLocation.getPlaceAddress());
    }

}