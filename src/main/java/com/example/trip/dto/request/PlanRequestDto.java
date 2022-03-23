package com.example.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
public class PlanRequestDto {

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Regist{
        private String title;

        private String travel_destination;

        private LocalDateTime travel_start;

        private LocalDateTime travel_end;

        private List<MemberRequestDto.join> memberList;
    }

    @NoArgsConstructor
    @Getter
    public static class Modify{
        private String title;

        private String travel_destination;

        private LocalDateTime travel_start;

        private LocalDateTime travel_end;

        private List<MemberRequestDto.join> memberList;

        private Boolean del_fl;
    }
}