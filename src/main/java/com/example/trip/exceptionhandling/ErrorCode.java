package com.example.trip.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 BAD_REQUEST : 잘못된 요청

    // 401 UNAUTHORIZED

    // 404 NOT FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),

    // 409 CONFLICT (중복된 데이터 존재)
    ALREADY_EXIST_USERNAME(HttpStatus.CONFLICT, "중복된 유저명입니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;


}
