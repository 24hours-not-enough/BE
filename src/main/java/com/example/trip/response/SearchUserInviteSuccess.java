package com.example.trip.response;

import com.example.trip.dto.SearchUserInviteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchUserInviteSuccess {
    private String result;
    private String msg;
    private SearchUserInviteResponseDto data;
}
