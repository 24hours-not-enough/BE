package com.example.trip.repository;

import com.example.trip.domain.Feed;
import com.example.trip.domain.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<FeedComment, Long> {
    @Query("select c from FeedComment c where c.id = ?1 and c.user.id = ?2")
    FeedComment findByIdAndUserId(Long commentId, Long userId);
}
