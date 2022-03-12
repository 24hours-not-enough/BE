package com.example.trip.service;

import com.example.trip.dto.request.MemberRequestDto;
import com.example.trip.dto.response.MemberResponseDto;

import java.util.List;

public interface MemberService {
    List<MemberResponseDto.invite> addMember(Long planId, MemberRequestDto.invite dto);
}
