package com.example.trip.repository;

import com.example.trip.domain.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CommentRepository extends JpaRepository<FeedComment, Long> {
    //피드 댓글과 사용자 id로 찾기
    @Query("select c from FeedComment c where c.id = ?1 and c.user.id = ?2")
    FeedComment findByIdAndUserId(Long commentId, Long userId);
}
