package com.example.trip.service;

import com.example.trip.dto.request.CheckListsRequestDto;

public interface CheckListService {
    void addCheckList(Long planId, CheckListsRequestDto dto, Long userId);

    void modifyCheckList(Long checkListsId, CheckListsRequestDto dto, Long planId, Long userId);

    void removeCheckList(Long checkListsId, Long planId, Long userId);
}
