package com.example.trip.controller;

import com.example.trip.advice.MemberInviteAll;
import com.example.trip.advice.MemberInviteUser;
import com.example.trip.advice.Success;
import com.example.trip.config.security.UserDetailsImpl;
import com.example.trip.dto.request.MemberRequestDto;
import com.example.trip.dto.response.MemberResponseDto;
import com.example.trip.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/plan/{planId}")
    public ResponseEntity<MemberInviteAll> planMemberOne(@PathVariable Long planId, @RequestBody MemberRequestDto.invite dto) {
        List<MemberResponseDto.invite> invites = memberService.addMember(planId, dto);
        return new ResponseEntity<>(new MemberInviteAll(true,"멤버초대 완료!",invites), HttpStatus.OK);
    }

    @GetMapping("/member/plan/{planId}")
    public ResponseEntity<MemberInviteAll> MemberList(@PathVariable Long planId) {
        List<MemberResponseDto.invite> memberList = memberService.findMember(planId);
        return new ResponseEntity<>(new MemberInviteAll(true,"함께하는 멤버조회 완료!",memberList), HttpStatus.OK);
    }

    @DeleteMapping("/member/plan/{planId}/user")
    public ResponseEntity<Success> MemberRemove(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId){
        memberService.removeMember(planId,userDetails.getUser().getId());
        return new ResponseEntity<>(new Success(true,"친구 초대 거절 완료!"), HttpStatus.OK);
    }

    @DeleteMapping("/member/plan/{planId}")
    public ResponseEntity<Success> MemberRemoveByRep(@PathVariable Long planId, @RequestBody MemberRequestDto memberRequestDto){
        memberService.removeMemberOne(planId,memberRequestDto);
        return new ResponseEntity<>(new Success(true,"친구 초대 취소 완료!"), HttpStatus.OK);
    }

    @GetMapping("/member/plan/invite")
    public ResponseEntity<MemberInviteUser> MemberList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<MemberResponseDto.inviteList> memberInviteList = memberService.findMemberInviteList(userDetails.getUser().getId());
        return new ResponseEntity<>(new MemberInviteUser(true,"초대 요청 리스트 조회 완료!",memberInviteList), HttpStatus.OK);
    }

    @PostMapping("/member/plan/{planId}")
    public ResponseEntity<Success> MemberInviteActive(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long planId){
        memberService.modifyMemberActive(userDetails.getUser().getId(),planId);
        return new ResponseEntity<>(new Success(true,"초대 요청 수락 완료!"),HttpStatus.OK);
    }
}
