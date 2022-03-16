package com.example.trip.feed;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {
    private String result;
    private String msg;
}
