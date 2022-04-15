package com.example.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewFeedLocationDto {
    @NotBlank
    private String name;

    @NotBlank
    private double latitude;

    @NotBlank
    private double longitude;

    @NotBlank
    private String placeAddress;
}
