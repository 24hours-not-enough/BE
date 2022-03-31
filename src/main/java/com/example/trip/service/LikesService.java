package com.example.trip.service;

import com.example.trip.domain.User;

public interface LikesService {
    void likeFeed(Long feedDetailLocId, User user);
    void unlikeFeed(Long feedDetailLocId, User user);
}
