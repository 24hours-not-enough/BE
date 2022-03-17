package com.example.trip.service;

import com.example.trip.dto.request.MemberRequestDto;
import com.example.trip.dto.response.MemberResponseDto;

import java.util.List;

public interface MemberService {
    List<MemberResponseDto.invite> addMember(Long userId, Long planId, MemberRequestDto.invite dto);

    List<MemberResponseDto.invite> findMember(Long userId, Long planId);

    void removeMember(Long planId, Long userId);

    void removeMemberOne(Long userId, Long planId, MemberRequestDto memberRequestDto);

    List<MemberResponseDto.inviteList> findMemberInviteList(Long userId, Long planId);

    void modifyMemberActive(Long userId, Long planId);

    void addMemberByUuid(Long userId, String roomId);
}
