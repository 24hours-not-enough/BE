package com.example.trip.service.impl;

import com.example.trip.advice.exception.AuthPlanNotFoundException;
import com.example.trip.advice.exception.PlanNotFoundException;
import com.example.trip.domain.Member;
import com.example.trip.domain.Plan;
import com.example.trip.domain.User;
import com.example.trip.dto.request.MemberRequestDto;
import com.example.trip.dto.response.MemberResponseDto;
import com.example.trip.repository.MemberRepository;
import com.example.trip.repository.UserRepository;
import com.example.trip.repository.plan.PlanRepository;
import com.example.trip.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final UserRepository userRepository;

    private final PlanRepository planRepository;

    @Override
    @Transactional
    public List<MemberResponseDto.invite> addMember(Long userId, Long planId, MemberRequestDto.invite dto) {
        authMemberValidation(userId, planId);
        planValidation(planId);
        setMember(planId, dto);
        return memberRepository.findPlanAndMembers(planId).stream()
                .map(MemberResponseDto.invite::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberResponseDto.invite> findMember(Long userId, Long planId) {
        planValidation(planId);
        memberAndPlanValidation(userId, planId);
        Optional<Plan> findPlanId = planRepository.findById(planId);
        return memberRepository.findPlanAndMembers(findPlanId.get().getId()).stream()
                .map(MemberResponseDto.invite::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeMember(Long planId, Long userId) {
        planValidation(planId);
        memberAndPlanValidation(userId, planId);
        memberRepository.deleteByPlanAndUser(planId,userId);
    }

    @Override
    public void removeMemberOne(Long userId, Long planId, MemberRequestDto memberRequestDto) {
        planValidation(planId);
        authMemberValidation(userId,planId);
        Optional<User> findUserByNickName = userRepository.findByNickName(memberRequestDto.getNickName());
        memberRepository.deleteByPlanAndUser(planId,findUserByNickName.get().getId());
    }

    @Override
    public List<MemberResponseDto.inviteList> findMemberInviteList(Long userId, Long planId) {
        planValidation(planId);
        authMemberValidation(userId,planId);
        return memberRepository.findByUserMemberList(userId).stream()
                .map(MemberResponseDto.inviteList::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void modifyMemberActive(Long userId, Long planId) {
        planValidation(planId);
        Optional<Member> byUserAndPland = Optional.ofNullable(memberRepository.findByUserAndPland(userId, planId).orElseThrow(AuthPlanNotFoundException::new));
        byUserAndPland.get().modifyActive();
    }

    @Override
    @Transactional
    public void addMemberByUuid(Long userId, String roomId) {
        Optional<Plan> findPlan = Optional.ofNullable(planRepository.findByUuid(roomId).orElseThrow(PlanNotFoundException::new));
        Optional<User> findUser = userRepository.findById(userId);
        Optional<Member> byUserAndPland = memberRepository.findByUserAndPland(userId, findPlan.get().getId());
        if(!byUserAndPland.isPresent()) {
            Member member = Member.builder()
                    .user(findUser.get())
                    .room_rep(false)
                    .plan(findPlan.get())
                    .active(true)
                    .build();
            memberRepository.save(member);
        }
    }

    private void setMember(Long planId, MemberRequestDto.invite dto) {
        dto.getMemberList().forEach((members) -> {
            Optional<Plan> findPlan = planRepository.findById(planId);
            Optional<User> findUser = userRepository.findByNickName(members.getNickName());
            Optional<Member> findMember = memberRepository.findByNickNameAndPlanId(findUser.get().getId(), planId);
            if (!findMember.isPresent()) {
                if (findPlan.isPresent()) {
                    Member member = Member.builder()
                            .user(findUser.get())
                            .room_rep(false)
                            .plan(findPlan.get())
                            //서비스 알림 기능 없으므로 임시로 모두 활성화 처리
                            .active(true)
                            .build();
                    memberRepository.save(member);
                }
            }
        });
    }

    private void memberAndPlanValidation(Long userId, Long planId) {
        memberRepository.findByUserAndPland(userId, planId).orElseThrow(AuthPlanNotFoundException::new);
    }

    private void planValidation(Long planId) {
        planRepository.findById(planId).orElseThrow(PlanNotFoundException::new);
    }

    private void authMemberValidation(Long userId, Long planId) {
        memberRepository.findByAuthMemberAndPlan(userId, planId).orElseThrow(AuthPlanNotFoundException::new);
    }
}
