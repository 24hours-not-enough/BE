package com.example.trip.repository;

import com.example.trip.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query("select f from Feed f where f.id = :feedId")
    Feed findFeedByFeedId(@Param("feedId") Long feedId);

    @Query("select f from Feed f left join fetch f.user u where u.id = :userId and f.id = :feedId")
    Feed FindFeedByUserId(Long userId, Long feedId);

    List<Feed> findByUserId(Long userId);

}
