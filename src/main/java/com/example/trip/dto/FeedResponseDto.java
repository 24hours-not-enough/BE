package com.example.trip.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {
    private String result;
    private String msg;
    private Object data;
}
