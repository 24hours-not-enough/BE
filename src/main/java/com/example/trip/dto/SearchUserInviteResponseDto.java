package com.example.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchUserInviteResponseDto {
    private String file_store_course;
    private String nickname;
    private Long user_id;
}
