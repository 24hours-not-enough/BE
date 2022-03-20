package com.example.trip.repository;

import com.example.trip.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Likes L WHERE L.feedDetailLoc.id = ?1 and L.user.id = ?2")
    void deleteLikeFeed(Long feedDetailLocId, Long userId);
}
