package com.example.trip.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequest {

    @Getter
    @NoArgsConstructor
    public static class joinDto{
        private String email;
    }
}
