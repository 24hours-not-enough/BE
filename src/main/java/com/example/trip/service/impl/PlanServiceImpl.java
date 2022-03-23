package com.example.trip.service.impl;

import com.example.trip.advice.exception.AuthPlanNotFoundException;
import com.example.trip.advice.exception.PlanNotFoundException;
import com.example.trip.advice.exception.UserNotFoundException;
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
    public Long addPlan(Long user_id, PlanRequestDto.Regist dto) {
        Optional<User> findUser = userRepository.findById(user_id);
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
    public List<PlanResponseDto> findPlan(Long userId, Pageable pageable) {
        return planRepository.findPlanAndMembers(userId).stream()
                .map(PlanResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void modifyPlan(Long user_id, Long planId, PlanRequestDto.Modify modify) {
        Optional<Plan> findPlan = Optional.ofNullable(planRepository.findById(planId).orElseThrow(PlanNotFoundException::new));
        authMemberValidation(user_id,planId);
        authPlanValidation(planId,user_id);
        if(modify.getDelFl()==null){
            findPlan.get().updatePlan(modify);
            memberRepository.deleteByPlanId(planId);
            setMember(modify.getMemberList(),findPlan.get());
        }else{
            findPlan.get().updatePlan(modify);
        }
    }

    @Override
    public PlanResponseDto findPlanOne(Long userId, Long planId) {
        planValidation(planId);
        authPlanValidation(planId,userId);
        return planRepository.findPlanAndMemberOne(planId);
    }

    @Override
    @Transactional
    public void removePlanMember(Long userId, Long planId) {
        planValidation(planId);
        authPlanValidation(planId,userId);
        memberRepository.deleteByPlanAndUser(planId,userId);
    }

    @Override
    @Transactional
    public void removePlan(Long planId, Long userId) {
        planValidation(planId);
        authPlanValidation(planId,userId);
        planRepository.deleteById(planId);
    }

    @Override
    public List<PlanResponseDto.DetailAll> findPlanAllAndMember(Long userId) {
        return planRepository.findPlanDetails()
                .stream()
                .map(PlanResponseDto.DetailAll::new)
                .collect(Collectors.toList());
    }

    //회원가입쪽 완료 시 동일 이메일 예외처리 넣어줘야함
    private void setMember(List<MemberRequestDto.join> memberList, Plan savePlan) {
        memberList.forEach((members) ->{
            Optional<User> findNickName = Optional.ofNullable(userRepository.findByNickName(members.getNickName()).orElseThrow(UserNotFoundException::new));
            Optional<Member> findMember = memberRepository.findByNickNameAndPlanId(findNickName.get().getId(),savePlan.getId());
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
            }
        });
    }


    private void authPlanValidation(Long planId, Long userId) {
        memberRepository.findByUserAndPland(userId,planId).orElseThrow(AuthPlanNotFoundException::new);
    }

    private void planValidation(Long planId) {
        planRepository.findById(planId).orElseThrow(PlanNotFoundException::new);
    }

    private void authMemberValidation(Long userId, Long planId) {
        memberRepository.findByAuthMemberAndPlan(userId, planId).orElseThrow(AuthPlanNotFoundException::new);
    }
}
