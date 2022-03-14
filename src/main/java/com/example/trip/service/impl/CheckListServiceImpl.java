package com.example.trip.service.impl;

import com.example.trip.domain.CheckList;
import com.example.trip.domain.Plan;
import com.example.trip.dto.request.CheckListsRequestDto;
import com.example.trip.repository.CheckListRepository;
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

    @Override
    @Transactional
    public void addCheckList(Long planId, CheckListsRequestDto dto) {
        System.out.println("dto = " + dto.getIs_checked());
        Optional<Plan> findPlan = planRepository.findById(planId);
        CheckList checkList = CheckList.builder()
                .check_item(dto.getCheckName())
                .is_checked(dto.getIs_checked())
                .plan(findPlan.get())
                .build();
        checkListRepository.save(checkList);
    }

    @Override
    @Transactional
    public void modifyCheckList(Long checkListsId, CheckListsRequestDto dto) {
        Optional<CheckList> findCheckList = checkListRepository.findById(checkListsId);
        findCheckList.get().updateCheckList(dto);

    }
}
