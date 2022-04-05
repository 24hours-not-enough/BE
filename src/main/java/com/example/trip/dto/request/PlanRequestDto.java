package com.example.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
public class PlanRequestDto {

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Regist{
        @NotBlank
        private String title;

        @NotBlank
        private String travelDestination;

        @NotBlank
        private LocalDateTime travelStart;

        @NotBlank
        private LocalDateTime travelEnd;

        private List<MemberRequestDto.join> memberList;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Modify{
        private String title;

        private String travelDestination;

        private LocalDateTime travelStart;

        private LocalDateTime travelEnd;

        private List<MemberRequestDto.join> memberList;

        private Boolean delFl;
    }
}