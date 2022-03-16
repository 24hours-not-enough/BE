package com.example.trip.feed;

import com.example.trip.domain.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<FeedComment, Long> {

}
