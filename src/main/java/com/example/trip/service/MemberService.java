package com.example.trip.service;

import com.example.trip.dto.request.MemberRequestDto;
import com.example.trip.dto.response.MemberResponseDto;

import java.util.List;

public interface MemberService {
    List<MemberResponseDto.invite> addMember(Long planId, MemberRequestDto.invite dto);

    List<MemberResponseDto.invite> findMember(Long planId);

    void removeMember(Long planId, Long userId);

    void removeMemberOne(Long planId, MemberRequestDto memberRequestDto);

    List<MemberResponseDto.inviteList> findMemberInviteList(Long userId);

    void modifyMemberActive(Long userId, Long planId);
}
