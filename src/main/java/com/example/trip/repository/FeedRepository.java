package com.example.trip.repository;

import com.example.trip.domain.Feed;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query("select f from Feed f left join fetch f.user u where u.id = :userId and f.id = :feedId")
    Optional<Feed> FindFeedByUserId(Long userId, Long feedId);

    List<Feed> findByUserId(Long userId);

}
