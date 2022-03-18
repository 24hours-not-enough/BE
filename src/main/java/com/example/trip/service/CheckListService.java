package com.example.trip.service;

import com.example.trip.dto.request.CheckListsRequestDto;

import java.util.List;

public interface CheckListService {
    void addCheckList(Long planId, List<CheckListsRequestDto> dto, Long userId);

    void modifyCheckList(Long checkListsId, CheckListsRequestDto dto, Long planId, Long userId);

    void removeCheckList(Long checkListsId, Long planId, Long userId);
}
