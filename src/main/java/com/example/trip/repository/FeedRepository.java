package com.example.trip.repository;

import com.example.trip.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    //
    @Query("select f from Feed f left join fetch f.user u where u.id = :userId and f.id = :feedId")
    Optional<Feed> FindFeedByUserId(Long userId, Long feedId);

    // 사용자 Id로 찾기
    List<Feed> findByUserId(Long userId);

    //사용자 Id와 피드 Id로 찾기
    @Query("select f from Feed f where f.id = ?1 and f.user.id = ?2")
    List<Feed> findByIdAndUserId(Long feedId, Long userId);

}

