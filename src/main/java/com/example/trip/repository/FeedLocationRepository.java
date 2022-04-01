package com.example.trip.repository;

import com.example.trip.domain.FeedDetail;
import com.example.trip.domain.FeedLocation;
import com.example.trip.dto.request.FeedRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedLocationRepository extends JpaRepository<FeedLocation, Long> {
    @Query("SELECT L FROM FeedLocation L WHERE L.latitude >= ?1 and L.latitude <= ?2 and L.longitude >= ?3 and L.longitude <= ?4")
    List<FeedLocation> findLocations(Long x1, Long x2, Long y1, Long y2);
}
