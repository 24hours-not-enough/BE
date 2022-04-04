package com.example.trip.repository.feedlocation;

import com.example.trip.domain.FeedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FeedLocationRepository extends JpaRepository<FeedLocation, Long>, FeedLocationCustomRepository {
    @Query("select fl from FeedLocation fl inner join BookMark bm on fl.id = bm.feedLocation.id where bm.user.id = ?1")
    List<FeedLocation> findFeedLocationByBookMarkByUser(Long userId);

//    @Query(value = "SELECT * FROM feed_location fl " +
//            "inner join book_mark bm on fl.feed_location_id = bm.feedlocation_id and bm.user_id =?1 " +
//            "inner join feed_detail_loc fdl on fdl.feed_location_id = fl.feed_location_id " +
//            "left join feed_detail_loc_img img on fdl.feed_detail_loc_id = img.feed_detail_loc_id " +
//            "left join feed_detail fd on fd.feed_detail_id = fdl.feed_detail_id " +
//            "left join feed feed on feed.feed_id = fd.feed_id " +
//            "left join likes li on li.feed_detail_loc_id = fdl.feed_detail_loc_id " +
//            "left join user user on user.user_id = li.user_id " +
//            "left join feed_comment com on com.feed_detail_loc_id = fdl.feed_detail_loc_id " +
//            "left join user user1 on user1.user_id = com.user_id", nativeQuery = true)
//    List<FeedLocation> findBookMarkLocation(Long userId);
@Query("SELECT L FROM FeedLocation L WHERE L.latitude >= ?1 and L.latitude <= ?2 and L.longitude >= ?3 and L.longitude <= ?4")
List<FeedLocation> findLocations(Long x1, Long x2, Long y1, Long y2);
}
