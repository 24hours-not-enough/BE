package com.example.trip.service.impl;

import com.example.trip.advice.exception.AuthPlanNotFoundException;
import com.example.trip.advice.exception.CalendarModifyException;
import com.example.trip.advice.exception.PlanNotFoundException;
import com.example.trip.domain.Calendar;
import com.example.trip.domain.Plan;
import com.example.trip.domain.User;
import com.example.trip.dto.response.CalendarResponseDto;
import com.example.trip.repository.CalendarRepository;
import com.example.trip.repository.MemberRepository;
import com.example.trip.repository.UserRepository;
import com.example.trip.repository.plan.PlanRepository;
import com.example.trip.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;

    private final PlanRepository planRepository;

    private final MemberRepository memberRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public CalendarResponseDto.CalendarAdd addDays(Long planId, Long userId) {
        planValidation(planId);
        authPlanValidation(planId, userId);
        List<Calendar> daysByPlanId = calendarRepository.findDaysByPlanId(planId);
        Optional<User> findUser = userRepository.findById(userId);
        Optional<Calendar> first = daysByPlanId.stream().findFirst();
        Optional<Plan> findPlan = planRepository.findById(planId);
        Optional<Calendar> calendarList = calendarRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).stream().findFirst();
        Calendar save;
        if(!first.isPresent()){
            Calendar calendar = Calendar.builder()
                    .days("1일차")
                    .plan(findPlan.get())
                    .is_locked(false)
                    .user(findUser.get())
                    .build();
            save = calendarRepository.save(calendar);
        }else {
            String day = first.get().getDays();
            int intDay = Integer.parseInt(day.replaceAll("[^0-9]", ""));

            Calendar calendar = Calendar.builder()
                    .days(intDay + 1 + "일차")
                    .plan(findPlan.get())
                    .is_locked(false)
                    .user(findUser.get())
                    .build();
            save = calendarRepository.save(calendar);
        }
            return CalendarResponseDto.CalendarAdd.builder()
                    .calendarId(save.getId()).build();
    }

    @Override
    @Transactional
    public void addCalendarLock(Long planId, Long userId) {
        planValidation(planId);
        authPlanValidation(planId, userId);
        List<Calendar> findCalendar = calendarRepository.findByPlanLock(planId);
        Optional<Calendar> planLock = findCalendar.stream().findFirst();
        if (planLock.isPresent()) {
            throw new CalendarModifyException();
        }
        List<Calendar> calendarList = calendarRepository.findByPlan(planId);
        Optional<User> findUser = userRepository.findById(userId);
        calendarList.forEach((list)-> list.updateCalendarLock(findUser.get())); //(Calendar::updateCalendarLock)
    }

    @Override
    @Transactional
    public void addCalendarUnLock(Long planId, Long userId) {
        List<Calendar> findPlan = calendarRepository.findByPlan(planId);
        Optional<User> findUser = userRepository.findById(userId);
        findPlan.forEach((list)->{
            list.CalendarUnLock(findUser.get());
        });
    }

    private void authPlanValidation(Long planId, Long userId) {
        memberRepository.findByUserAndPlanActive(planId, userId).orElseThrow(AuthPlanNotFoundException::new);
    }

    private void planValidation(Long planId) {
        planRepository.findById(planId).orElseThrow(PlanNotFoundException::new);
    }
}
