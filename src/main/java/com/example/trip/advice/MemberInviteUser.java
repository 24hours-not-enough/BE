package com.example.trip.advice;

import com.example.trip.dto.response.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MemberInviteUser {

    private boolean success;

    private String msg;

    private List<MemberResponseDto.inviteList> data;
}
