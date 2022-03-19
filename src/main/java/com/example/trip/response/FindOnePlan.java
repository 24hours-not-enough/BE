package com.example.trip.response;

import com.example.trip.dto.MypageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindOnePlan {
    private String result;
    private String msg;
    private MypageResponseDto.GetPlan plan;
}
