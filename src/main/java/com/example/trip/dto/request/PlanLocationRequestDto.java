package com.example.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanLocationRequestDto {

    private String location;

    private String latitude;

    private String longitude;
}
