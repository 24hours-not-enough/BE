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

        private String travelDestination;

        private LocalDateTime travelStart;

        private LocalDateTime travelEnd;

        private List<MemberRequestDto.join> memberList;
    }

    @NoArgsConstructor
    @Getter
    public static class Modify{
        private String title;

        private String travelDestination;

        private LocalDateTime travelStart;

        private LocalDateTime travelEnd;

        private List<MemberRequestDto.join> memberList;

        private Boolean delFl;
    }
}