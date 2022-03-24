package com.example.trip.repository;

import com.example.trip.domain.FeedComment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    @Query("select c from FeedComment c where c.feedDetailLoc.id = :feeddetaillocId")
    List<FeedComment> FindFeedCommentByFeedDetailLocId(@Param("feeddetaillocId") Long feeddetaillocId);

    @Query("SELECT F FROM FeedComment F WHERE F.feedDetailLoc.id = ?1 and F.user.id= ?2")
    List<FeedComment> findByIdAndUserId(Long feedDetailLocId, Long userId);
}
