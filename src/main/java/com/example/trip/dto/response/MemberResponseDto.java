package com.example.trip.dto.response;

import com.example.trip.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private String email;

    public MemberResponseDto(Member member) {
        this.email = member.getUser().getEmail();
    }
}
