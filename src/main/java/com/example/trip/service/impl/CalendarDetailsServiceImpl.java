package com.example.trip.service.impl;

import com.example.trip.advice.exception.*;
import com.example.trip.domain.Calendar;
import com.example.trip.domain.CalendarDetails;
import com.example.trip.domain.User;
import com.example.trip.dto.request.CalendarDetailsRequestDto;
import com.example.trip.dto.response.CalendarResponseDto;
import com.example.trip.repository.CalendarDetailsRepository;
import com.example.trip.repository.CalendarRepository;
import com.example.trip.repository.MemberRepository;
import com.example.trip.repository.UserRepository;
import com.example.trip.repository.plan.PlanRepository;
import com.example.trip.service.CalendarDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CalendarDetailsServiceImpl implements CalendarDetailsService {

    private final CalendarDetailsRepository calendarDetailsRepository;

    private final CalendarRepository calendarRepository;

    private final PlanRepository planRepository;

    private final MemberRepository memberRepository;

    private final UserRepository userRepository;
    @Override
    @Transactional
    public void addCalendarDetails(Long calendar_id, CalendarDetailsRequestDto.Add dto, Long planId, Long userId) {
        planValidation(planId);
        calendarValidation(calendar_id);
        userAndPlanValidation(planId,userId);
        Optional<Calendar> findByCalendar = Optional.ofNullable(calendarRepository.findById(calendar_id).orElseThrow(CalendarNotFoundException::new));
        CalendarDetails calendarDetails = CalendarDetails.builder()
                .calendar(findByCalendar.get())
                .name(dto.getLocation())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .build();
        calendarDetailsRepository.save(calendarDetails);
    }

    @Override
    @Transactional
    public void modifyCalendarDetails(Long planId, Long calendarId, CalendarDetailsRequestDto.Modify dto, Long userId) {
        planValidation(planId);
        calendarValidation(calendarId);
        userAndPlanValidation(planId,userId);
        //Optional<CalendarDetails> findByDetails = calendarDetailsRepository.findById(calendarId);
        Optional<Calendar> findcalendarId = Optional.ofNullable(calendarRepository.findById(calendarId).orElseThrow(CalendarNotFoundException::new));
        planValidation(planId);
        calendarDetailsRepository.deleteByCalendarId(calendarId);
        setCalendarDetails(findcalendarId.get(),dto);
    }

    @Override
    @Transactional
    public void removeCalendarDetails(Long planId, Long calendarId, Long userId) {
        planValidation(planId);
        calendarValidation(calendarId);
        userAndPlanValidation(planId,userId);
        calendarDetailsRepository.deleteByCalendarId(calendarId);
    }

    @Override
    public List<CalendarResponseDto> findCalendarDetails(Long planId, Long calendarId, Long userId) {
        planValidation(planId);
        calendarValidation(calendarId);
        userAndPlanValidation(planId,userId);
        return calendarRepository.findByPlanIdAndCalendarId(planId).stream()
                .map(CalendarResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addCalendarDetailsAll(Long planId, List<CalendarDetailsRequestDto.AddAll> dto, Long userId) {
        planValidation(planId);
        userAndPlanValidation(planId,userId);
        List<Calendar> byPlan = calendarRepository.findByPlan(planId);
        Optional<User> findUser = userRepository.findById(userId);
        dto.forEach((list)-> byPlan.forEach((planList-> planList.updateCalendarUnlock(list.getDays(), findUser.get()))));
        setCalendarDetailsList(dto);
    }

    private void setCalendarDetailsList(List<CalendarDetailsRequestDto.AddAll> dto) {
        dto.forEach((details)->{
            Optional<Calendar> findCalendar = Optional.ofNullable(calendarRepository.findById(details.getCalendarId()).orElseThrow(CalendarNotFoundException::new));
            List<CalendarDetails> findCalendarDetailsByCalendarId = calendarDetailsRepository.findByPlanIdAndCalendarId(details.getCalendarId());
            Optional<CalendarDetails> calendarDetail = findCalendarDetailsByCalendarId.stream().findFirst();
            if(calendarDetail.isPresent()){
                throw new CalendarDetailsAlreadyExistException();
            }else {
                details.getCalendarDetails().forEach((detailsList) -> {
                    CalendarDetails calendarDetails = CalendarDetails.builder()
                            .calendar(findCalendar.get())
                            .name(detailsList.getLocation())
                            .latitude(detailsList.getLatitude())
                            .longitude(detailsList.getLongitude())
                            .memo(detailsList.getLocationMemo())
                            .sort(detailsList.getSort())
                            .build();
                    calendarDetailsRepository.save(calendarDetails);
                });
            }
        });
    }

    private void setCalendarDetails(Calendar calendar, CalendarDetailsRequestDto.Modify dto) {
        dto.getDetailsList().forEach((detailsList)->{
            CalendarDetails calendarDetails = CalendarDetails.builder()
                    .calendar(calendar)
                    .name(detailsList.getLocation())
                    .latitude(detailsList.getLatitude())
                    .longitude(detailsList.getLongitude())
                    .memo(detailsList.getLocationMemo())
                    .sort(detailsList.getSort())
                    .build();
            calendarDetailsRepository.save(calendarDetails);
        });
    }

    private void planValidation(Long planId) {
        planRepository.findById(planId).orElseThrow(PlanNotFoundException::new);
    }

    private void calendarValidation(Long calendarId){
        calendarRepository.findById(calendarId).orElseThrow(CalendarNotFoundException::new);
    }

    private void userAndPlanValidation(Long planId,Long userId){
        memberRepository.findByUserAndPlanActive(planId, userId).orElseThrow(AuthPlanNotFoundException::new);
    }
}
