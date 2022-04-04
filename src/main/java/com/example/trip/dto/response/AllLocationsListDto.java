package com.example.trip.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class AllLocationsListDto {
    //모든 지역별 피드 정보를 담고 있는 리스트
    private List<AllLocationsDto> allLocationsDtoList;
}
