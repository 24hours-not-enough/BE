package com.example.trip.service.impl;

import com.example.trip.advice.exception.AuthPlanNotFoundException;
import com.example.trip.advice.exception.CheckListModifyException;
import com.example.trip.advice.exception.CheckListNotFoundException;
import com.example.trip.advice.exception.PlanNotFoundException;
import com.example.trip.domain.CheckList;
import com.example.trip.domain.Plan;
import com.example.trip.domain.User;
import com.example.trip.dto.request.CheckListsRequestDto;
import com.example.trip.repository.CheckListRepository;
import com.example.trip.repository.MemberRepository;
import com.example.trip.repository.UserRepository;
import com.example.trip.repository.plan.PlanRepository;
import com.example.trip.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CheckListServiceImpl implements CheckListService {

    private final CheckListRepository checkListRepository;

    private final PlanRepository planRepository;

    private final MemberRepository memberRepository;

    private final UserRepository userRepository;

    private final RedisServiceImpl redisServiceImpl;

    @Override
    @Transactional
    public void addCheckList(Long planId, List<CheckListsRequestDto> dto, Long userId) {
        Optional<Plan> findPlan = Optional.ofNullable(planRepository.findById(planId).orElseThrow(PlanNotFoundException::new));
        memberRepository.findByUserAndPlanActive(planId, userId).orElseThrow(AuthPlanNotFoundException::new);
        List<CheckList> byPlanId = checkListRepository.findByPlanId(planId);
        Optional<User> byId = userRepository.findById(userId);
        checkListRepository.deleteByPlanId(planId);
        setCheckList(findPlan.get(),dto, byId.get());

    }

    private void setCheckList(Plan plan, List<CheckListsRequestDto> dto, User user) {
        dto.forEach((checkList)->{
            CheckList checklist = CheckList.builder()
                    .check_item(checkList.getCheckName())
                    .is_checked(checkList.getIsChecked())
                    .is_locked(false)
                    .plan(plan)
                    .user(user)
                    .build();
            checkListRepository.save(checklist);
        });
    }

    @Override
    public void modifyCheckList(Long planId, List<CheckListsRequestDto> dto, Long userId) {

    }

    @Override
    @Transactional
    public void removeCheckList(Long checkListsId, Long planId, Long userId) {
        planValidation(planId);
        userAndPlanValidation(planId,userId);
        checkListValidation(checkListsId);
        checkListRepository.deleteById(checkListsId);
    }

    @Override
    @Transactional
    public void addCheckListLock(Long planId, Long id) {
        planValidation(planId);
        userAndPlanValidation(planId,id);
        List<CheckList> findLockCheckList = checkListRepository.findByPlanIdAndLock(planId);
        Optional<CheckList> PlanIdAndLock = findLockCheckList.stream().findFirst();
        if (PlanIdAndLock.isPresent()) {
            throw new CheckListModifyException();
        }
        List<CheckList> findPlan = checkListRepository.findByPlanId(planId);
        Optional<User> findUser = userRepository.findById(id);
        findPlan.forEach((list)->{
           list.updateCheckListLock(findUser.get());
        });
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
