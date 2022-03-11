package com.example.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
public class PlanRequest {

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class RegistDto{
        private String title;

        private String travel_destination;

        private LocalDateTime travel_start;

        private LocalDateTime travel_end;

        private List<MemberRequest.joinDto> memberList;

        private Boolean del_tc;
    }
}