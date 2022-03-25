package com.example.trip.repository;

import com.example.trip.domain.FeedDetailLoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedDetailLocRepository extends JpaRepository<FeedDetailLoc, Long> {
    @Query("SELECT L.id FROM FeedDetailLoc  L INNER JOIN L.feedDetail F INNER JOIN F.feed fe WHERE fe.id = ?1 GROUP BY L.id")
    List<Long> findByFeedId(Long feedId);

    // 총 도시 리스트
//    @Query(value = "SELECT * FROM feed_detail_loc fdl inner join likes li on fdl.feed_detail_loc_id = li.feed_detail_loc_id inner join user us on li.user_id = ?1 group by fdl.city", nativeQuery = true)
//    List<FeedDetailLoc> FindAllCityList(Long userId);

    // 특정 도시에 대한 것만
//    @Query(value = "SELECT * FROM feed_detail_loc fdl inner join likes li on fdl.feed_detail_loc_id = li.feed_detail_loc_id inner join user us on li.user_id = ?1 where fdl.city = ?2", nativeQuery = true)
//    List<FeedDetailLoc> FindOneCityList(Long userId, String city);

//    @Query(value = "SELECT * FROM FeedDetailLoc fdl inner join Likes li on li.user_id = ?1 inner join FeedLocation fl where li.feed_detail_loc_id = fdl.feed_detail_loc_id and fl.feed_location_id = fdl.feed_location_id and fl.place_address= ?2", nativeQuery = true)

//    @Query("select fdl from FeedDetailLoc fdl inner join Likes li on li.user.id = ?1 inner join FeedLocation fl where li.feedDetailLoc.id = fdl.id and fl.id = fdl.feedLocation.id and fl.placeAddress = ?2")
    @Query(value = "SELECT * FROM feed_detail_loc fdl inner join likes li on li.user_id = :userId inner join feed_location fl where li.feed_detail_loc_id = fdl.feed_detail_loc_id and fl.feed_location_id = fdl.feed_location_id and fl.place_address = :address", nativeQuery = true)
    List<FeedDetailLoc> findLocationsByLikes(Long userId, String address);

    @Query(value = "SELECT distinct * FROM feed_detail_loc fdl inner join likes li on li.user_id = 1 inner join feed_location fl where li.feed_detail_loc_id = fdl.feed_detail_loc_id and fl.feed_location_id = fdl.feed_location_id", nativeQuery = true)
    List<FeedDetailLoc> findAddressCnt(Long userId);

//    @Query("select distinct fl.placeAddress from FeedDetailLoc fdl inner join Likes li on li.user.id = ?1 inner join FeedLocation fl where li.feedDetailLoc.id = fdl.id and fl.id = fdl.feedLocation.id")

    @Query("SELECT F FROM FeedDetailLoc  F WHERE F.feedDetail.id = ?1")
    List<FeedDetailLoc> findByFeedDetailId(Long feedDetailID);

}
