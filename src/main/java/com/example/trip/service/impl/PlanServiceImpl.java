package com.example.trip.service.impl;

import com.example.trip.domain.Member;
import com.example.trip.domain.Plan;
import com.example.trip.domain.User;
import com.example.trip.dto.request.MemberRequestDto;
import com.example.trip.dto.request.PlanRequestDto;
import com.example.trip.dto.response.PlanResponseDto;
import com.example.trip.repository.MemberRepository;
import com.example.trip.repository.UserRepository;
import com.example.trip.repository.plan.PlanRepository;
import com.example.trip.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlanServiceImpl implements PlanService {

    private final UserRepository userRepository;

    private final PlanRepository planRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long addPlan(PlanRequestDto.Regist dto) {
        Optional<User> findUser = userRepository.findById(1L);
        Plan plan = Plan.of(findUser.get(),dto);
        Plan savePlan = planRepository.save(plan);
        Member member = Member.builder()
                .room_rep(true)
                .plan(savePlan)
                .user(findUser.get())
                .active(true)
                .build();
        memberRepository.save(member);
        setMember(dto.getMemberList(), savePlan);
        return savePlan.getId();
    }

    @Override
    public List<PlanResponseDto> findPlan(Pageable pageable) {
        return planRepository.findPlanAndMembers().stream()
                .map(PlanResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void modifyPlan(Long planId, PlanRequestDto.Modify modify) {
        Optional<Plan> findPlan = planRepository.findById(planId);
        if(modify.getDel_tc()!=null){
            findPlan.get().deletePlan(modify);
        }else {
            findPlan.get().updatePlan(modify);
            memberRepository.deleteByPlanId(planId);
            setMember(modify.getMemberList(),findPlan.get());
        }
    }

    @Override
    public PlanResponseDto findPlanOne(Long planId) {
        return planRepository.findPlanAndMemberOne(planId);
    }

    @Override
    public void removePlanMember(Long id, Long planId) {
        memberRepository.deleteByPlanAndUser(planId,id);
    }

    //회원가입쪽 완료 시 동일 이메일 예외처리 넣어줘야함
    private void setMember(List<MemberRequestDto.joinDto> memberList, Plan savePlan) {
        memberList.forEach((members) ->{
            System.out.println("members.getNickName() = " + members.getNickName());
            Optional<User> findNickName = userRepository.findByNickName(members.getNickName());
            System.out.println("findNickName = " +findNickName);
            Optional<Member> findMember = memberRepository.findByNickNameAndPlanId(findNickName.get().getId(),savePlan.getId());
            System.out.println("findMember = " + findMember);
            //Optional<User> findUser = userRepository.findByEmail(findMember.get()..getEmail());
            if(!findMember.isPresent()){
               // if(findUser.isPresent()) {
                    Member member = Member.builder()
                          //  .email(members.getEmail())
                            .user(findNickName.get())
                            .room_rep(false)
                            .plan(savePlan)
                            .active(false)
                            .build();
                    memberRepository.save(member);
              //  }
            }
        });
    }
}
