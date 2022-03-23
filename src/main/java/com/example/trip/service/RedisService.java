package com.example.trip.service;

public interface RedisService {

    // 키-밸류값 설정하기
    void setValues(String token, String snsId);

    // 키값으로 벨류 가져오기
    String getValues(String token);

    // 키-벨류 삭제
    void delValues(String token);
}
