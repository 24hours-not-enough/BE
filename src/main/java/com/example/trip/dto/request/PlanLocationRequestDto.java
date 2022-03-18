package com.example.trip.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanLocationRequestDto {

    private String location;

    private String latitude;

    private String longitude;
}
