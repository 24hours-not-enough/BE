package com.example.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class CheckListsRequestDto {

    private String checkName;

    private Boolean isChecked;
}
