package com.example.trip.repository.feedlocation;

import com.example.trip.dto.response.FeedLocationResponseDto;
import java.util.List;

public interface FeedLocationCustomRepository {
    List<FeedLocationResponseDto.BookMark> findBookMarkLocation(Long userId);
}
