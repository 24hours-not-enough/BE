package com.example.trip.advice;

import com.example.trip.dto.response.MemberResponseDto;
import com.example.trip.dto.response.PlanResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MemberInviteAll {

    private boolean success;

    private String msg;

    private List<MemberResponseDto.invite> data;
}
