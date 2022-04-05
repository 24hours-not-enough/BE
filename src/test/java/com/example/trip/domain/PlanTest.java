package com.example.trip.domain;

import com.example.trip.dto.request.MemberRequestDto;
import com.example.trip.dto.request.PlanLocationRequestDto;
import com.example.trip.dto.request.PlanRequestDto;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlanTest {
    Long id;
    String title;
    String travelDestination;
    LocalDateTime travelStart;
    LocalDateTime travelEnd;
    Boolean del_tc;
    String uuid;
    Boolean delFl;

    @BeforeEach
    void init(){
        id = 1L;
        title = "제목";
        travelDestination = "목적지";
        travelStart = LocalDateTime.MIN;
        travelEnd = LocalDateTime.MAX;
        del_tc = false;
        uuid = "random";
        delFl = false;

    }


    @Test
    @DisplayName("계획 등록 정상 케이스")
    void createPlan_Normal() {

        PlanRequestDto.Regist requestDto = PlanRequestDto.Regist.builder()
                .title(title)
                .travelDestination(travelDestination)
                .travelStart(travelStart)
                .travelEnd(travelEnd)
                .build();

// when
        Plan plan = Plan.builder()
                .title(requestDto.getTitle())
                .travel_destination(requestDto.getTravelDestination())
                .travel_start(requestDto.getTravelStart())
                .travel_end(requestDto.getTravelEnd())
                .build();

// then
        assertNull(plan.getId());
        assertNull(plan.getDel_tc());
        assertNull(plan.getUuid());
        assertEquals(title, requestDto.getTitle());
        assertEquals(travelDestination, requestDto.getTravelDestination());
        assertEquals(travelStart, requestDto.getTravelStart());
        assertEquals(travelEnd, requestDto.getTravelEnd());
    }

    @Test
    @DisplayName("계획 수정 정상 케이스")
    void modifyPlan_Normal() {

        PlanRequestDto.Modify requestDto = PlanRequestDto.Modify.builder()
                .title(title)
                .travelDestination(travelDestination)
                .travelStart(travelStart)
                .travelEnd(travelEnd)
                .delFl(delFl)
                .build();

// when
        Plan plan = Plan.builder()
                .title(requestDto.getTitle())
                .travel_destination(requestDto.getTravelDestination())
                .travel_start(requestDto.getTravelStart())
                .travel_end(requestDto.getTravelEnd())
                .build();

// then
        assertNull(plan.getId());
        assertNull(plan.getDel_tc());
        assertNull(plan.getUuid());
        assertEquals(title, requestDto.getTitle());
        assertEquals(travelDestination, requestDto.getTravelDestination());
        assertEquals(travelStart, requestDto.getTravelStart());
        assertEquals(travelEnd, requestDto.getTravelEnd());
        assertEquals(delFl, requestDto.getDelFl());
    }


}
