package com.example.trip.repository;

import com.example.trip.domain.FeedDetailLoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedDetailLocRepository extends JpaRepository<FeedDetailLoc, Long> {
    @Query("SELECT L.id FROM FeedDetailLoc  L INNER JOIN L.feedDetail F INNER JOIN F.feed fe WHERE fe.id = ?1 GROUP BY L.id")
    List<Long> findByFeedId(Long feedId);
}
