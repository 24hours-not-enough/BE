package com.example.trip.repository;

import com.example.trip.domain.FeedDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FeedDetailRepository extends JpaRepository<FeedDetail, Long> {

    @Query("SELECT F FROM FeedDetail F WHERE F.feed.id = ?1")
    List<FeedDetail> findByFeedId(Long feedId);

    @Transactional
    @Modifying
    @Query("DELETE FROM FeedDetail F WHERE F.feed.id = ?1")
    void deleteByFeedId(Long feedId);
}
