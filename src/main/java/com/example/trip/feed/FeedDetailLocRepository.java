package com.example.trip.feed;

import com.example.trip.domain.FeedDetail;
import com.example.trip.domain.FeedDetailLoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedDetailLocRepository extends JpaRepository<FeedDetailLoc, Long> {
}
