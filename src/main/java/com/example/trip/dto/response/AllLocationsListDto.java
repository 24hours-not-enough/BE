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
    private List<AllLocationsDto> allLocationsDtoList;
}
