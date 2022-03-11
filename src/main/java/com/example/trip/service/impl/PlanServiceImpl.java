package com.example.trip.service.impl;

import com.example.trip.domain.Member;
import com.example.trip.domain.Plan;
import com.example.trip.domain.User;
import com.example.trip.dto.request.MemberRequest;
import com.example.trip.dto.request.PlanRequest;
import com.example.trip.repository.MemberRepository;
import com.example.trip.repository.PlanRepository;
import com.example.trip.repository.UserRepository;
import com.example.trip.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlanServiceImpl implements PlanService {

    private final UserRepository userRepository;

    private final PlanRepository planRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void addPlan(PlanRequest.RegistDto dto) {
        Optional<User> findUser = userRepository.findById(1L);
        Plan plan = Plan.of(findUser.get(),dto);
        Plan savePlan = planRepository.save(plan);
        Member member = Member.builder()
                .email(findUser.get().getEmail())
                .room_rep(true)
                .plan(savePlan)
                .build();
        memberRepository.save(member);
        setMember(dto.getMemberList(), savePlan);
    }

    //회원가입쪽 완료 시 동일 이메일 예외처리 넣어줘야함
    private void setMember(List<MemberRequest.joinDto> memberList, Plan savePlan) {
        memberList.forEach((members) ->{
            Member member = Member.builder()
                    .email(members.getEmail())
                    .room_rep(false)
                    .plan(savePlan)
                    .build();
            memberRepository.save(member);
        });
    }
}
