package com.example.trip.service.impl;

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
    public List<MemberResponseDto.invite> addMember(Long planId, MemberRequestDto.invite dto) {
        setMember(planId, dto);
        return memberRepository.findPlanAndMembers(planId).stream()
                .map(MemberResponseDto.invite::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberResponseDto.invite> findMember(Long planId) {
        Optional<Plan> findPlanId = planRepository.findById(planId);
        return memberRepository.findPlanAndMembers(findPlanId.get().getId()).stream()
                .map(MemberResponseDto.invite::new)
                .collect(Collectors.toList());
    }

    @Override
    public void removeMember(Long planId, Long userId) {
        memberRepository.deleteByPlanAndUser(planId,userId);

    }

    @Override
    public void removeMemberOne(Long planId, MemberRequestDto memberRequestDto) {
        Optional<User> findUserByNickName = userRepository.findByNickName(memberRequestDto.getNickName());
        memberRepository.deleteByPlanAndUser(planId,findUserByNickName.get().getId());
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
                            .active(false)
                            .build();
                    memberRepository.save(member);
                }
            }
        });
    }
}
