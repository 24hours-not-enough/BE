package com.example.trip.service;

import com.example.trip.dto.request.CheckListsRequestDto;

public interface CheckListService {
    void addCheckList(Long planId, CheckListsRequestDto dto);

    void modifyCheckList(Long checkListsId, CheckListsRequestDto dto);
}
