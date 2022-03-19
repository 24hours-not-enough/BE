package com.example.trip.repository;

import com.example.trip.domain.Feed;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
<<<<<<< HEAD
=======
import org.springframework.data.repository.query.Param;
>>>>>>> dev

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query("select f from Feed f where f.id = :feedId")
    Feed findFeedByFeedId(@Param("feedId") Long feedId);

    @Query("select f from Feed f left join fetch f.user u where u.id = :userId and f.id = :feedId")
    Feed FindFeedByUserId(Long userId, Long feedId);

    List<Feed> findByUserId(Long userId);

}
