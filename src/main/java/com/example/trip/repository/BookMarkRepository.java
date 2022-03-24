package com.example.trip.repository;

import com.example.trip.domain.BookMark;
import com.example.trip.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
//    @Transactional
//    @Modifying
//    @Query("DELETE FROM BookMark B WHERE B.feedDetailLoc.id = ?1 and B.user.id = ?2")
//    void deleteBookmarkFeed(Long feedDetailLocId, Long userId);

    @Query("select b from BookMark b where b.user.id = :userId")
    List<BookMark> FindBookmarkByUserId(@Param("userId") Long userId);

//    @Query("SELECT B FROM BookMark B WHERE B.feedDetailLoc.id = ?1 and B.user.id = ?2")
//    List<BookMark> findByFeedDetailLocIdAndUserId(Long feedDetailLocId, Long userId);

    List<BookMark> findByUserId(Long userId);

}
