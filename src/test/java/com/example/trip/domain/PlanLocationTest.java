package com.example.trip.domain;

import com.example.trip.dto.request.PlanLocationRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class PlanLocationTest {
//    @Test
//    public void testFile() {
//        String path = "./src/main/resources";
//        String fileName = "test.json";
//
//        File file = new File(path, fileName);
//
//        assertTrue(file.exists());
//    }


    @Test
    @DisplayName("정상 케이스")
    void createPlanLocation_Normal() {
// given
//            String path = "./src/main/resources";
//            String fileName = "test.json";
//
//            File file = new File(path, fileName);

        String location = "대구";
        String latitude = "1";
        String longitude = "10";

        PlanLocationRequestDto requestDto = new PlanLocationRequestDto(
                location,
                latitude,
                longitude
        );

// when
        PlanLocation planLocation = PlanLocation.builder()
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .name(location)
                .build();

// then
        assertNull(planLocation.getId());
        assertEquals(location, requestDto.getLocation());
        assertEquals(latitude, requestDto.getLatitude());
        assertEquals(longitude, requestDto.getLongitude());
    }


}
