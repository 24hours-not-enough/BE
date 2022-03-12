package com.example.trip.dto.response;

import com.example.trip.domain.Plan;
import com.example.trip.dto.request.MemberRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PlanResponseDto {

    private Long plan_id;

    private String title;

    private String travel_destination;

    private LocalDateTime travel_start;

    private LocalDateTime travel_end;

    private List<MemberResponseDto> memberList;

    private Boolean del_tc;

    @Builder
    public PlanResponseDto(Long plan_id, String title, String travel_destination, LocalDateTime travel_start, LocalDateTime travel_end){
        this.title = title;
        this.travel_destination = travel_destination;
        this.travel_start = travel_start;
        this.travel_end = travel_end;
    }

    public PlanResponseDto(Plan plan) {
        this.plan_id = plan.getId();
        this.title = plan.getTitle();
        this.travel_destination = plan.getTravel_destination();
        this.travel_start = plan.getTravel_start();
        this.travel_end = plan.getTravel_end();
        this.memberList = plan.getMembers().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
        this.del_tc = plan.getDel_tc();
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class RegistDto{

        private Long plan_id;
        private String title;

        private String travel_destination;

        private LocalDateTime travel_start;

        private LocalDateTime travel_end;

        private List<MemberRequestDto.joinDto> memberList;

        private Boolean del_tc;

        public RegistDto(Plan plan) {
            this.title= plan.getTitle();
            this.del_tc=plan.getDel_tc();
            this.travel_destination=plan.getTravel_destination();
        }
    }
}
