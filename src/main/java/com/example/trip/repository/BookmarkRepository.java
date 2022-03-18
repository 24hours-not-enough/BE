package com.example.trip.repository;

import com.example.trip.domain.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM BookMark B WHERE B.feedDetailLoc.id = ?1 and B.user.id = ?2")
    void deleteBookmarkFeed(Long feedDetailLocId, Long userId);
}
