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
    //북마크 삭제
    @Transactional
    @Modifying
    @Query("DELETE FROM BookMark B WHERE B.feedLocation.id = ?1 and B.user.id = ?2")
    void deleteBookmarkFeed(Long feedLocId, Long userId);

    @Query("select b from BookMark b where b.user.id = :userId")
    List<BookMark> FindBookmarkByUserId(@Param("userId") Long userId);

    //FeedLoc Id와 유저 Id로 북마크 찾기
    @Query("SELECT B FROM BookMark B WHERE B.feedLocation.id = ?1 and B.user.id = ?2")
    List<BookMark> findByFeedLocIdAndUserId(Long feedLocId, Long userId);

    List<BookMark> findByUserId(Long userId);

}

