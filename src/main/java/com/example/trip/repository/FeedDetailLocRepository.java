package com.example.trip.repository;

import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.FeedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedDetailLocRepository extends JpaRepository<FeedDetailLoc, Long> {
    @Query("SELECT L.id FROM FeedDetailLoc  L INNER JOIN L.feedDetail F INNER JOIN F.feed fe WHERE fe.id = ?1 GROUP BY L.id")
    List<Long> findByFeedId(Long feedId);

    @Query(value = "SELECT * FROM feed_detail_loc fdl inner join likes li on li.user_id = :userId inner join feed_location fl where li.feed_detail_loc_id = fdl.feed_detail_loc_id and fl.feed_location_id = fdl.feed_location_id and fl.place_address = :address", nativeQuery = true)
    List<FeedDetailLoc> findLocationsByLikes(Long userId, String address);

    @Query(value = "SELECT distinct * FROM feed_detail_loc fdl inner join likes li on li.user_id = 1 inner join feed_location fl where li.feed_detail_loc_id = fdl.feed_detail_loc_id and fl.feed_location_id = fdl.feed_location_id", nativeQuery = true)
    List<FeedDetailLoc> findAddressCnt(Long userId);

    @Query("SELECT F FROM FeedDetailLoc  F WHERE F.feedDetail.id = ?1")
    List<FeedDetailLoc> findByFeedDetailId(Long feedDetailID);
}
