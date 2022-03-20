package com.example.trip.repository;

import com.example.trip.domain.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<FeedComment, Long> {

}
