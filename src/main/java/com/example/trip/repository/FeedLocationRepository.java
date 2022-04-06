package com.example.trip.repository;

import com.example.trip.domain.FeedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedLocationRepository extends JpaRepository<FeedLocation, Long> {
    @Query("select fl from FeedLocation fl inner join BookMark bm on fl.id = bm.feedLocation.id where bm.user.id = ?1")
    List<FeedLocation> findFeedLocationByBookMarkByUser(Long userId);

    @Query("SELECT L FROM FeedLocation L WHERE L.latitude >= ?1 and L.latitude <= ?2 and L.longitude >= ?3 and L.longitude <= ?4")
    List<FeedLocation> findLocations(Long x1, Long x2, Long y1, Long y2);

}
