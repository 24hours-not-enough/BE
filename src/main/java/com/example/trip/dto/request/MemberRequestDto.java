package com.example.trip.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String nickName;

    @Getter
    @NoArgsConstructor
    public static class joinDto{
        private String nickName;
    }

    @Getter
    @NoArgsConstructor
    public static class invite{
        private List<MemberRequestDto> memberList;
    }
}
