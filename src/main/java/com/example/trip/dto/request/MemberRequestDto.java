package com.example.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String nickName;

    @Getter
    @NoArgsConstructor
    public static class join{
        private String nickName;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class invite{
        private List<MemberRequestDto> memberList;
    }
}

