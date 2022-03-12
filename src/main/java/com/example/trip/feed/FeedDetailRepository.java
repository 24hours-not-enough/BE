package com.example.trip.feed;

import com.example.trip.domain.FeedDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedDetailRepository extends JpaRepository<FeedDetail, Long> {
}
