package com.example.trip.dto.response;

import com.example.trip.domain.*;
import com.example.trip.dto.request.MemberRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
public class PlanResponseDto {

    private Long planId;

    private String title;

    private String travelDestination;

    private LocalDateTime travelStart;

    private LocalDateTime travelEnd;

    private List<MemberResponseDto> memberList;

    private Boolean delFl;

    @Builder
    public PlanResponseDto(Long plan_id, String title, String travel_destination, LocalDateTime travel_start, LocalDateTime travel_end) {
        this.title = title;
        this.travelDestination = travel_destination;
        this.travelStart = travel_start;
        this.travelEnd = travel_end;
    }

    public PlanResponseDto(Plan plan) {
        this.planId = plan.getId();
        this.title = plan.getTitle();
        this.travelDestination = plan.getTravel_destination();
        this.travelStart = plan.getTravel_start();
        this.travelEnd = plan.getTravel_end();
        this.memberList = plan.getMembers().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
        this.delFl = plan.getDel_tc();
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Regist {

        private Long planId;
        private String title;

        private String travelDestination;

        private LocalDateTime travelStart;

        private LocalDateTime travelEnd;

        private List<MemberRequestDto.join> memberList;

        private Boolean delFl;

        public Regist(Plan plan) {
            this.title = plan.getTitle();
            this.delFl = plan.getDel_tc();
            this.travelDestination = plan.getTravel_destination();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailAll {
        private Long planId;

        private String title;

        private String travelDestination;

        private LocalDateTime travelStart;

        private LocalDateTime travelEnd;

        private Boolean delTc;

        private String roomId;

        private MemberResponseDto creator;

        private List<MemberResponseDto> members;

        private List<CalendarResponseDto> calendars;

        private List<CheckListResponseDto> checkLists;

        public DetailAll(Plan plan) {
            List<MemberResponseDto> collect = plan.getMembers().stream().filter(Member::getRoom_rep).map(MemberResponseDto::new).collect(Collectors.toList());
            this.planId = plan.getId();
            this.title = plan.getTitle();
            this.travelDestination = plan.getTravel_destination();
            this.travelStart = plan.getTravel_start();
            this.travelEnd = plan.getTravel_end();
            this.delTc = plan.getDel_tc();
            this.roomId = plan.getUuid();
            this.creator = collect.get(0);
            this.members = plan.getMembers().stream()
                    .filter(member -> !member.getRoom_rep())
                    .map(MemberResponseDto::new)
                    .collect(Collectors.toList());
            this.calendars = plan.getCalendars().stream()
                    .map(CalendarResponseDto::new)
                    .collect(Collectors.toList());
            this.checkLists = plan.getCheckLists().stream()
                    .map(CheckListResponseDto::new)
                    .collect(Collectors.toList());
        }
    }

    @AllArgsConstructor
    @Getter
    public static class GetPlan {
        private boolean success;

        private String msg;

        private Long data;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String result;
        private String msg;
        private Object data;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseNodata{
        private String result;
        private String msg;
    }


    @AllArgsConstructor
    @Getter
    public static class GetAllPlanDetails {
        private boolean success;

        private String msg;

        private List<PlanResponseDto.DetailAll> data;
    }
}