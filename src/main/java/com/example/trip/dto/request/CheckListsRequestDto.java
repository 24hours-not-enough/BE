package com.example.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckListsRequestDto {

    private String checkName;

    private Boolean is_checked;
}
