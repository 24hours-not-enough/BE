package com.example.trip.service.impl;

import com.example.trip.advice.exception.AuthPlanNotFoundException;
import com.example.trip.advice.exception.CheckListNotFoundException;
import com.example.trip.advice.exception.PlanNotFoundException;
import com.example.trip.domain.CheckList;
import com.example.trip.domain.Plan;
import com.example.trip.dto.request.CheckListsRequestDto;
import com.example.trip.repository.CheckListRepository;
import com.example.trip.repository.MemberRepository;
import com.example.trip.repository.plan.PlanRepository;
import com.example.trip.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CheckListServiceImpl implements CheckListService {

    private final CheckListRepository checkListRepository;

    private final PlanRepository planRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void addCheckList(Long planId, CheckListsRequestDto dto, Long userId) {
        System.out.println("dto = " + dto.getIs_checked());
        Optional<Plan> findPlan = Optional.ofNullable(planRepository.findById(planId).orElseThrow(PlanNotFoundException::new));
        memberRepository.findByUserAndPlanActive(planId, userId).orElseThrow(AuthPlanNotFoundException::new);
        CheckList checkList = CheckList.builder()
                .check_item(dto.getCheckName())
                .is_checked(dto.getIs_checked())
                .plan(findPlan.get())
                .build();
        checkListRepository.save(checkList);
    }

    @Override
    @Transactional
    public void modifyCheckList(Long checkListsId, CheckListsRequestDto dto, Long planId, Long userId) {
        planValidation(planId);
        userAndPlanValidation(planId,userId);
        checkListValidation(checkListsId);
        Optional<CheckList> findCheckList = checkListRepository.findById(checkListsId);
        findCheckList.get().updateCheckList(dto);

    }

    @Override
    @Transactional
    public void removeCheckList(Long checkListsId, Long planId, Long userId) {
        planValidation(planId);
        userAndPlanValidation(planId,userId);
        checkListValidation(checkListsId);
        checkListRepository.deleteById(checkListsId);
    }

    private void planValidation(Long planId) {
        planRepository.findById(planId).orElseThrow(PlanNotFoundException::new);
    }

    private void userAndPlanValidation(Long planId,Long userId){
        memberRepository.findByUserAndPlanActive(planId, userId).orElseThrow(AuthPlanNotFoundException::new);
    }

    private void checkListValidation(Long checkListsId){
        checkListRepository.findById(checkListsId).orElseThrow(CheckListNotFoundException::new);
    }


}