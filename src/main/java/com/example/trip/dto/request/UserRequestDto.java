package com.example.trip.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class UserRequestDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CheckUsername {
        @NotBlank
        private String username;
    }
}
