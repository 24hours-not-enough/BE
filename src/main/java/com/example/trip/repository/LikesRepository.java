package com.example.trip.repository;

import com.example.trip.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    List<Likes> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Likes L WHERE L.feedDetailLoc.id = ?1 and L.user.id = ?2")
    public void deleteLikeFeed(Long feedDetailLocId, Long userId);


}
